package com.cafe.manager.assignment.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.cafe.manager.Application;
import com.cafe.manager.enums.OrderStatus;
import com.cafe.manager.enums.Role;
import com.cafe.manager.exceptions.OpenedOrderExistingException;
import com.cafe.manager.model.Order;
import com.cafe.manager.model.OrderRequest;
import com.cafe.manager.model.Product;
import com.cafe.manager.model.ProductInOrder;
import com.cafe.manager.model.Table;
import com.cafe.manager.model.User;
import com.cafe.manager.service.OrderService;
import com.cafe.manager.service.ProductService;
import com.cafe.manager.service.TableService;
import com.cafe.manager.service.UserService;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IntegrationTest {

    private long managerId;
    private long waiterId;

    @Autowired
    private UserService userService;

    @Autowired
    private TableService tableService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductService productService;

    @Before
    public void setup() {
        managerId = createUser("manager", "manager", Role.ROLE_MANAGER);
        waiterId = createUser("waiter", "waiter", Role.ROLE_WAITER);
    }

    @Test
    public void testAddOrder() {
        Table cafeTable = tableService.createTable();
        Product product = productService.createProduct(new Product("Coca Cola", 350));

        tableService.assignWaiter(cafeTable.getId(), waiterId);

        Order order = orderService.createOrder(waiterId,
                new OrderRequest(
                        cafeTable.getId(),
                        Collections.singletonList(
                                new ProductInOrder(UUID.randomUUID(), product.getId(), 2)
                        )
                )
        );

        assertNotNull(order.getId());
    }

    @Test
    public void testCloseOrder() {
        Table table = tableService.createTable();
        Product product = productService.createProduct(new Product("Coca Cola", 350));

        tableService.assignWaiter(table.getId(), waiterId);

        Order order = orderService.createOrder(waiterId,
                new OrderRequest(
                        table.getId(),
                        Collections.singletonList(
                                new ProductInOrder(UUID.randomUUID(), product.getId(), 2)
                        )
                )
        );

        assertNotNull(order.getId());

        orderService.editOrderStatus(waiterId, order.getId(), OrderStatus.CLOSED);

        order = orderService.createOrder(waiterId,
                new OrderRequest(
                        table.getId(),
                        Collections.singletonList(
                                new ProductInOrder(UUID.randomUUID(), product.getId(), 2)
                        )
                )
        );

        assertNotNull(order.getId());
    }

    @Test(expected = OpenedOrderExistingException.class)
    public void testOrderFailWhenOpenOrderExists() {
        Table table = tableService.createTable();
        Product product = productService.createProduct(new Product("Coca Cola", 350));

        tableService.assignWaiter(table.getId(), waiterId);

        for (int i = 0; i < 2; i++) {
            orderService.createOrder(waiterId,
                    new OrderRequest(
                            table.getId(),
                            Collections.singletonList(
                                    new ProductInOrder(UUID.randomUUID(), product.getId(), 2)
                            )
                    )
            );
        }
    }

    @Test
    public void testAssignWaiter() {
        List<Table> assignedCafeTables;
        Table table = tableService.createTable();

        assignedCafeTables = tableService.getAllTablesAssignedToWaiter(waiterId);
        assertEquals(0, assignedCafeTables.size());

        tableService.assignWaiter(table.getId(), waiterId);
        assignedCafeTables = tableService.getAllTablesAssignedToWaiter(waiterId);
        assertEquals(1, assignedCafeTables.size());
    }

    private long createUser(String username, String password, Role role) {
        User user = new User(
                "Suren",
                "Sharyan",
                username + "@gmail.com",
                username,
                password,
                role
        );

        User createdUser = userService.createUser(user);
        return createdUser.getId();
    }

    @After
    public void tearDown() {
        userService.deleteUser(waiterId);
        userService.deleteUser(managerId);
    }

}
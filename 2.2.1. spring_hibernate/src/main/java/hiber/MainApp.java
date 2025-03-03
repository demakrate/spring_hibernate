package hiber;

import hiber.config.AppConfig;
import hiber.model.Car;
import hiber.model.User;
import hiber.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;
import java.util.List;

public class MainApp {
    public static void main(String[] args) throws SQLException {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(AppConfig.class);

        UserService userService = context.getBean(UserService.class);
        User user1 = new User("Dmitriy", "Polyakov", "user1@mail.ru");
        user1.setCar(new Car("Mercedes", 111));
        userService.add(user1);
        User user2 = new User("Alex", "Alexov", "user2@mail.ru");
        user2.setCar(new Car("BMV", 222));
        userService.add(user2);
        User user3 = new User("Kianu", "Rivs", "user3@mail.ru");
        user3.setCar(new Car("Toyota", 333));
        userService.add(user3);
        User user4 = new User("Donald", "Potter", "user4@mail.ru");
        user4.setCar(new Car("Nissan", 444));
        userService.add(user4);

        List<User> users = userService.listUsers();
        for (User user : users) {
            System.out.println("Id = " + user.getId());
            System.out.println("First Name = " + user.getFirstName());
            System.out.println("Last Name = " + user.getLastName());
            System.out.println("Email = " + user.getEmail());
            System.out.println("Car = " + user.getCar());
            System.out.println();
        }

        System.out.println(userService.getUserByCar("BMV", 222).toString());

        context.close();
    }
}

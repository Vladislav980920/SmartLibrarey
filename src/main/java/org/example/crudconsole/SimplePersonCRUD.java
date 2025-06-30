package org.example.crudconsole;

import java.sql.Connection;
import java.util.Scanner;

public class SimplePersonCRUD {
    public static void main(String[] args) {
        try {
            // Установка соединения с базой данных
            Connection connection = DatabaseConnector.getConnection();

            // Создание репозитория и сервиса
            PersonRepository repository = new PersonRepository(connection);
            Scanner scanner = new Scanner(System.in);
            PersonService service = new PersonService(repository, scanner);

            // Главный цикл программы
            boolean exit = false;
            while (!exit) {
                printMainMenu();
                System.out.print("Выберите действие: ");
                String input = scanner.nextLine();

                try {
                    int choice = Integer.parseInt(input);

                    switch (choice) {
                        case 1:
                            service.addPerson();
                            break;
                        case 2:
                            service.viewAllPersons();
                            break;
                        case 3:
                            service.viewPersonById();
                            break;
                        case 4:
                            service.updatePerson();
                            break;
                        case 5:
                            service.deletePerson();
                            break;
                        case 6:
                            service.searchByLastName();
                            break;
                        case 7:
                            service.viewSortedPersons();
                            break;
                        case 8:
                            service.batchInsert();
                            break;
                        case 9:
                            exit = true;
                            System.out.println("Выход из программы...");
                            break;
                        default:
                            System.out.println("Неверный выбор. Попробуйте снова.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Пожалуйста, введите число от 1 до 9.");
                }
            }

            // Закрытие ресурсов
            scanner.close();
            connection.close();
        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void printMainMenu() {
        System.out.println("\n=== Главное меню ===");
        System.out.println("1. Добавить пользователя");
        System.out.println("2. Просмотреть всех пользователей");
        System.out.println("3. Найти пользователя по ID");
        System.out.println("4. Обновить данные пользователя");
        System.out.println("5. Удалить пользователя");
        System.out.println("6. Поиск по фамилии");
        System.out.println("7. Просмотр с сортировкой");
        System.out.println("8. Массовое добавление");
        System.out.println("9. Выход");
    }
}
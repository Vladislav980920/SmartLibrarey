package org.example.crudconsole;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PersonService {
    private PersonRepository repository;
    private Scanner scanner;

    public PersonService(PersonRepository repository, Scanner scanner) {
        this.repository = repository;
        this.scanner = scanner;
    }

    public void addPerson() {
        try {
            Person person = new Person();

            System.out.print("Введите имя: ");
            person.setFirstName(scanner.nextLine());

            System.out.print("Введите фамилию: ");
            person.setLastName(scanner.nextLine());

            System.out.print("Введите email: ");
            person.setEmail(scanner.nextLine());

            System.out.print("Введите зарплату: ");
            person.setSalary(Double.parseDouble(scanner.nextLine()));

            System.out.print("Введите отдел (не обязательно): ");
            String department = scanner.nextLine();
            person.setDepartment(department.isEmpty() ? null : department);

            repository.addPerson(person);
            System.out.println("Пользователь успешно добавлен!");
        } catch (SQLException e) {
            System.out.println("Ошибка при добавлении пользователя: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Неверный формат зарплаты. Введите число.");
        }
    }

    public void viewAllPersons() {
        try {
            List<Person> persons = repository.getAllPersons();
            PersonPrinter.printPersonsTable(persons);
        } catch (SQLException e) {
            System.out.println("Ошибка при получении списка пользователей: " + e.getMessage());
        }
    }

    public void viewPersonById() {
        try {
            System.out.print("Введите ID пользователя: ");
            int id = Integer.parseInt(scanner.nextLine());

            Person person = repository.getPersonById(id);
            if (person != null) {
                PersonPrinter.printPersonDetails(person);
            } else {
                System.out.println("Пользователь с ID " + id + " не найден.");
            }
        } catch (SQLException e) {
            System.out.println("Ошибка при поиске пользователя: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Неверный формат ID. Введите число.");
        }
    }

    public void updatePerson() {
        try {
            System.out.print("Введите ID пользователя для обновления: ");
            int id = Integer.parseInt(scanner.nextLine());

            Person person = repository.getPersonById(id);
            if (person == null) {
                System.out.println("Пользователь с ID " + id + " не найден.");
                return;
            }

            System.out.println("Введите новые данные (оставьте пустым, чтобы не изменять):");

            System.out.print("Имя: ");
            String firstName = scanner.nextLine();
            if (!firstName.isEmpty()) {
                person.setFirstName(firstName);
            }

            System.out.print("Фамилия: ");
            String lastName = scanner.nextLine();
            if (!lastName.isEmpty()) {
                person.setLastName(lastName);
            }

            System.out.print("Email: ");
            String email = scanner.nextLine();
            if (!email.isEmpty()) {
                person.setEmail(email);
            }

            System.out.print("Зарплата: ");
            String salaryStr = scanner.nextLine();
            if (!salaryStr.isEmpty()) {
                person.setSalary(Double.parseDouble(salaryStr));
            }

            System.out.print("Отдел: ");
            String department = scanner.nextLine();
            person.setDepartment(department.isEmpty() ? null : department);

            repository.updatePerson(person);
            System.out.println("Данные пользователя успешно обновлены!");
        } catch (SQLException e) {
            System.out.println("Ошибка при обновлении пользователя: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Неверный формат данных. Введите число для ID и зарплаты.");
        }
    }

    public void deletePerson() {
        try {
            System.out.print("Введите ID пользователя для удаления: ");
            int id = Integer.parseInt(scanner.nextLine());

            repository.deletePerson(id);
            System.out.println("Пользователь успешно удален!");
        } catch (SQLException e) {
            System.out.println("Ошибка при удалении пользователя: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Неверный формат ID. Введите число.");
        }
    }

    public void searchByLastName() {
        try {
            System.out.print("Введите фамилию или часть фамилии: ");
            String lastName = scanner.nextLine();

            List<Person> persons = repository.searchByLastName(lastName);
            PersonPrinter.printPersonsTable(persons);
        } catch (SQLException e) {
            System.out.println("Ошибка при поиске пользователей: " + e.getMessage());
        }
    }

    public void viewSortedPersons() {
        try {
            System.out.println("\nВарианты сортировки:");
            System.out.println("1. По зарплате (возрастание)");
            System.out.println("2. По зарплате (убывание)");
            System.out.println("3. По дате создания (возрастание)");
            System.out.println("4. По дате создания (убывание)");
            System.out.print("Выберите вариант: ");

            int sortChoice = Integer.parseInt(scanner.nextLine());

            String sortBy;
            boolean ascending;
            switch (sortChoice) {
                case 1:
                    sortBy = "salary";
                    ascending = true;
                    break;
                case 2:
                    sortBy = "salary";
                    ascending = false;
                    break;
                case 3:
                    sortBy = "create_date";
                    ascending = true;
                    break;
                case 4:
                    sortBy = "create_date";
                    ascending = false;
                    break;
                default:
                    System.out.println("Неверный выбор.");
                    return;
            }

            List<Person> persons = repository.getPersonsSorted(sortBy, ascending);
            PersonPrinter.printPersonsTable(persons);
        } catch (SQLException e) {
            System.out.println("Ошибка при сортировке пользователей: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Неверный формат выбора. Введите число от 1 до 4.");
        }
    }

    public void batchInsert() {
        try {
            System.out.println("\nМассовое добавление пользователей");
            System.out.println("Введите данные пользователей (оставьте имя пустым для завершения):");

            List<Person> persons = new ArrayList<>();
            while (true) {
                System.out.println("\nПользователь #" + (persons.size() + 1));

                System.out.print("Имя (пусто для завершения): ");
                String firstName = scanner.nextLine();
                if (firstName.isEmpty()) break;

                Person person = new Person();
                person.setFirstName(firstName);

                System.out.print("Фамилия: ");
                person.setLastName(scanner.nextLine());

                System.out.print("Email: ");
                person.setEmail(scanner.nextLine());

                System.out.print("Зарплата: ");
                person.setSalary(Double.parseDouble(scanner.nextLine()));

                System.out.print("Отдел: ");
                String department = scanner.nextLine();
                person.setDepartment(department.isEmpty() ? null : department);

                persons.add(person);
            }

            if (!persons.isEmpty()) {
                repository.batchInsert(persons);
                System.out.println("Успешно добавлено " + persons.size() + " пользователей.");
            } else {
                System.out.println("Не добавлено ни одного пользователя.");
            }
        } catch (SQLException e) {
            System.out.println("Ошибка при массовом добавлении: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Неверный формат зарплаты. Введите число.");
        }
    }
}
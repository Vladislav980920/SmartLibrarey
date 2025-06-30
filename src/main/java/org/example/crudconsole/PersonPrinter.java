package org.example.crudconsole;

import java.util.List;

public class PersonPrinter {
    public static void printPersonDetails(Person person) {
        System.out.println("\nИнформация о пользователе:");
        System.out.println("ID: " + person.getId());
        System.out.println("Имя: " + person.getFirstName());
        System.out.println("Фамилия: " + person.getLastName());
        System.out.println("Email: " + person.getEmail());
        System.out.println("Зарплата: " + person.getSalary());
        System.out.println("Отдел: " + (person.getDepartment() == null ? "N/A" : person.getDepartment()));
        System.out.println("Дата создания: " + person.getCreateDate());
    }

    public static void printPersonsTable(List<Person> persons) {
        System.out.println("\nID  Имя           Фамилия        Email                     Зарплата   Отдел         Дата создания");
        System.out.println("-------------------------------------------------------------------------------");

        if (persons.isEmpty()) {
            System.out.println("Нет данных для отображения.");
            return;
        }

        for (Person person : persons) {
            System.out.printf("%-4d%-14s%-15s%-26s%-11.2f%-14s%s%n",
                    person.getId(),
                    person.getFirstName(),
                    person.getLastName(),
                    person.getEmail(),
                    person.getSalary(),
                    person.getDepartment() == null ? "N/A" : person.getDepartment(),
                    person.getCreateDate());
        }
    }
}
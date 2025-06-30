package org.example.crudconsole;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class PersonRepository {
    private Connection connection;

    public PersonRepository(Connection connection) {
        this.connection = connection;
    }

    public void addPerson(Person person) throws SQLException {
        String sql = "INSERT INTO person (first_name, last_name, email, salary, department) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, person.getFirstName());
            statement.setString(2, person.getLastName());
            statement.setString(3, person.getEmail());
            statement.setDouble(4, person.getSalary());

            if (person.getDepartment() == null || person.getDepartment().isEmpty()) {
                statement.setNull(5, Types.VARCHAR);
            } else {
                statement.setString(5, person.getDepartment());
            }

            statement.executeUpdate();
        }
    }

    public List<Person> getAllPersons() throws SQLException {
        List<Person> persons = new ArrayList<>();
        String sql = "SELECT * FROM person ORDER BY id";

        try (Statement statement = connection.createStatement();
             ResultSet result = statement.executeQuery(sql)) {

            while (result.next()) {
                Person person = new Person();
                person.setId(result.getInt("id"));
                person.setFirstName(result.getString("first_name"));
                person.setLastName(result.getString("last_name"));
                person.setEmail(result.getString("email"));
                person.setSalary(result.getDouble("salary"));
                person.setDepartment(result.getString("department"));
                person.setCreateDate(result.getDate("create_date"));

                persons.add(person);
            }
        }

        return persons;
    }

    public Person getPersonById(int id) throws SQLException {
        String sql = "SELECT * FROM person WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);

            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    Person person = new Person();
                    person.setId(result.getInt("id"));
                    person.setFirstName(result.getString("first_name"));
                    person.setLastName(result.getString("last_name"));
                    person.setEmail(result.getString("email"));
                    person.setSalary(result.getDouble("salary"));
                    person.setDepartment(result.getString("department"));
                    person.setCreateDate(result.getDate("create_date"));

                    return person;
                }
            }
        }

        return null;
    }

    public void updatePerson(Person person) throws SQLException {
        String sql = "UPDATE person SET first_name = ?, last_name = ?, email = ?, salary = ?, department = ? WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, person.getFirstName());
            statement.setString(2, person.getLastName());
            statement.setString(3, person.getEmail());
            statement.setDouble(4, person.getSalary());

            if (person.getDepartment() == null || person.getDepartment().isEmpty()) {
                statement.setNull(5, Types.VARCHAR);
            } else {
                statement.setString(5, person.getDepartment());
            }

            statement.setInt(6, person.getId());
            statement.executeUpdate();
        }
    }

    public void deletePerson(int id) throws SQLException {
        String sql = "DELETE FROM person WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }

    public List<Person> searchByLastName(String lastName) throws SQLException {
        List<Person> persons = new ArrayList<>();
        String sql = "SELECT * FROM person WHERE last_name LIKE ? ORDER BY last_name";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, "%" + lastName + "%");

            try (ResultSet result = statement.executeQuery()) {
                while (result.next()) {
                    Person person = new Person();
                    person.setId(result.getInt("id"));
                    person.setFirstName(result.getString("first_name"));
                    person.setLastName(result.getString("last_name"));
                    person.setEmail(result.getString("email"));
                    person.setSalary(result.getDouble("salary"));
                    person.setDepartment(result.getString("department"));
                    person.setCreateDate(result.getDate("create_date"));

                    persons.add(person);
                }
            }
        }

        return persons;
    }

    public List<Person> getPersonsSorted(String sortBy, boolean ascending) throws SQLException {
        List<Person> persons = new ArrayList<>();
        String sql = "SELECT * FROM person ORDER BY " + sortBy + (ascending ? " ASC" : " DESC");

        try (Statement statement = connection.createStatement();
             ResultSet result = statement.executeQuery(sql)) {

            while (result.next()) {
                Person person = new Person();
                person.setId(result.getInt("id"));
                person.setFirstName(result.getString("first_name"));
                person.setLastName(result.getString("last_name"));
                person.setEmail(result.getString("email"));
                person.setSalary(result.getDouble("salary"));
                person.setDepartment(result.getString("department"));
                person.setCreateDate(result.getDate("create_date"));

                persons.add(person);
            }
        }

        return persons;
    }

    public void batchInsert(List<Person> persons) throws SQLException {
        String sql = "INSERT INTO person (first_name, last_name, email, salary, department) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            for (Person person : persons) {
                statement.setString(1, person.getFirstName());
                statement.setString(2, person.getLastName());
                statement.setString(3, person.getEmail());
                statement.setDouble(4, person.getSalary());

                if (person.getDepartment() == null || person.getDepartment().isEmpty()) {
                    statement.setNull(5, Types.VARCHAR);
                } else {
                    statement.setString(5, person.getDepartment());
                }

                statement.addBatch();
            }

            statement.executeBatch();
        }
    }
}
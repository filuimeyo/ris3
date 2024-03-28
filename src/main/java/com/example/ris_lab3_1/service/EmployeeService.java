package com.example.ris_lab3_1.service;


import com.example.ris_lab3_1.entity.Employee;
import jakarta.ejb.Stateful;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;

@Stateful
public class EmployeeService {
    @PersistenceContext(unitName="default")
    private EntityManager entityManager;

    public void addEmployee(Employee employee) {
        entityManager.persist(employee);
        entityManager.flush();
    }

    public List<Employee> getEmployees() {
        return entityManager.createQuery("SELECT e from Employee e", Employee.class)
                .getResultList();
    }

    public List<Employee> getEmployees(Integer departmentId) {
        String queryString = "SELECT e FROM Employee e WHERE e.department = :departmentId";

        return entityManager.createQuery(queryString, Employee.class)
                .setParameter("departmentId", departmentId)
                .getResultList();
    }

    public List<Employee> getEmployees(String name){

        name = "%" + name + "%";

        String queryString = "SELECT e FROM Employee e " +
                "WHERE e.firstName LIKE :name OR  e.lastName LIKE :name";

        return entityManager.createQuery(queryString, Employee.class)
                .setParameter("name", name)
                .getResultList();
    }

    public List<Employee> getEmployees(String name, Integer departmentId){

        name = "%" + name + "%";

        String queryString = "SELECT e FROM Employee e " +
                "WHERE e.firstName LIKE :name OR  e.lastName LIKE : name " +
                "AND e.department = :departmentId ";

        return entityManager.createQuery(queryString, Employee.class)
                .setParameter("name", name)
                .setParameter("departmentId", departmentId)
                .getResultList();
    }

    public Employee getEmployee(long id) {
        return entityManager.find(Employee.class, id);
    }

    public void updateEmployee(Employee employee) {
        entityManager.merge(employee);
        entityManager.flush();
    }

    public void deleteEmployee(long id) {
        Employee employee = getEmployee(id);
        entityManager.remove(employee);
        entityManager.flush();
    }

}

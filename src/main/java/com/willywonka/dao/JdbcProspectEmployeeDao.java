package com.willywonka.dao;

import com.willywonka.exception.DaoException;
import com.willywonka.model.ProspectEmployee;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.util.ArrayList;
import java.util.List;

public class JdbcProspectEmployeeDao implements ProspectEmployeeDao{

    private JdbcTemplate jdbcTemplate;

    public JdbcProspectEmployeeDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<ProspectEmployee> getAllProspectEmployees() {
        List<ProspectEmployee> prospectEmployees = new ArrayList<>();
        String sql = "SELECT prospect_employee_id, first_name, last_name, prospect_department_id FROM prospect_employee";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
            while (results.next()) {
                ProspectEmployee prospectEmployee = mapRowToUser(results);
                prospectEmployees.add(prospectEmployee);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e); //created custom exception to display specific message while also displaying root cause of exception
        }
        return prospectEmployees;
    }

    private ProspectEmployee mapRowToUser(SqlRowSet results) {
        ProspectEmployee prospectEmployee = new ProspectEmployee();
        prospectEmployee.setId(results.getInt("prospect_employee_id"));
        prospectEmployee.setFirstName(results.getString("prospect_first_name"));
        prospectEmployee.setLastName(results.getString("prospect_last_name"));
        prospectEmployee.setDept("prospect_employee_dept");
        return prospectEmployee;
    }

}
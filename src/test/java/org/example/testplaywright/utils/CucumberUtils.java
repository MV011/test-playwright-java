package org.example.testplaywright.utils;

import io.cucumber.datatable.DataTable;

import java.util.List;
import java.util.Map;

public class CucumberUtils {

    public static List<Map<String, String>> processDataTable(DataTable dataTable) {
        return dataTable.asMaps(String.class, String.class);
    }
}

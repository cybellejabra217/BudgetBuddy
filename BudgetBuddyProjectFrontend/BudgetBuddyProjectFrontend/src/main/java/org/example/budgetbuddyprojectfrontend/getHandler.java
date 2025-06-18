package org.example.budgetbuddyprojectfrontend;


import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.*;

// defining the getHandler class for all get requests
public class getHandler {

    private final String BASE_URL = "http://localhost:8080/api/";
    OkHttpClient client = new OkHttpClient();


    // handler to get all categories
    public String getAllCategories() {
        String url = BASE_URL + "categories";
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected error " + response);
            }
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
            return e.toString();
        }
    }

    // handler to get a category id by its name
    public String getCategoryIdByName(String name) {
        String url = BASE_URL + "categories/" + name;
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                return response.body().string();
            } else {
                return "Error: " + response.code();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "IO Exception: " + e.getMessage();
        }
    }


    // handler to get all expenses for a user
    public List<Map<String, Object>> getAllExpensesForUser(int userId) {
        String url = BASE_URL + "expenses/user/" + userId;
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                String jsonResponse = response.body().string();
                return parseExpensesResponse(jsonResponse);
            } else {
                System.err.println("Error: " + response.code());
                return Collections.emptyList();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    // private function to parse the response to needed format
    private List<Map<String, Object>> parseExpensesResponse(String jsonResponse) {
        List<Map<String, Object>> expenses = new ArrayList<>();
        JSONArray jsonArray = new JSONArray(jsonResponse);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            System.out.println(jsonObject);
            String categoryName = jsonObject.getString("categoryName");
            double amount = jsonObject.getDouble("amount");
            Map<String, Object> expenseMap = new HashMap<>();
            expenseMap.put("categoryName", categoryName);
            expenseMap.put("amount", amount);
            expenses.add(expenseMap);
        }
        return expenses;
    }

    // handler to get all incomes for user
    public List<Map<String, Object>> getAllIncomesForUser(int userId) {
        String url = BASE_URL + "incomes/user/" + userId;
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                String jsonResponse = response.body().string();
                return parseIncomesResponse(jsonResponse);
            } else {
                System.err.println("Error: " + response.code());
                return Collections.emptyList();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    // function to parse income response to needed format
    private List<Map<String, Object>> parseIncomesResponse(String jsonResponse) {
        List<Map<String, Object>> incomes = new ArrayList<>();
        JSONArray jsonArray = new JSONArray(jsonResponse);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            System.out.println(jsonObject);
            String sourceName = jsonObject.getString("sourceName");
            double amount = jsonObject.getDouble("amount");
            Map<String, Object> incomeMap = new HashMap<>();
            incomeMap.put("sourceName", sourceName);
            incomeMap.put("amount", amount);
            incomes.add(incomeMap);
        }
        return incomes;
    }

    // handler to get all reminders for a user
    public List<Map<String, Object>> getRemindersByUser(int userId) {
        String url = BASE_URL + "reminders/user/" + userId;
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                String jsonResponse = response.body().string();
                return parseRemindersResponse(jsonResponse);
            } else {
                System.err.println("Error: " + response.code());
                return Collections.emptyList();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    // function to parse reminder responses to needed format
    private List<Map<String, Object>> parseRemindersResponse(String jsonResponse) {
        List<Map<String, Object>> reminders = new ArrayList<>();
        JSONArray jsonArray = new JSONArray(jsonResponse);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String description = jsonObject.getString("description");
            String date = jsonObject.getString("date"); // Assuming the date is represented as a string
            Map<String, Object> reminderMap = new HashMap<>();
            reminderMap.put("description", description);
            reminderMap.put("reminderDate", date);
            reminders.add(reminderMap);
        }
        return reminders;
    }

    // handler to get all sources
    public String getAllSources() {
        String url = BASE_URL + "sources";
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                return response.body().string();
            } else {
                return "Error: " + response.code();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "IO Exception: " + e.getMessage();
        }
    }

    // handler to get source id by its name
    public String getSourceIdByName(String name) {
        String url = BASE_URL + "sources/" + name;
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                return response.body().string();
            } else {
                return "Error: " + response.code();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "IO Exception: " + e.getMessage();
        }
    }

    // handler to get the current user's id
    public int getCurrentUserId() {
        String url = BASE_URL + "user/currentUserId";
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                String responseBody = response.body().string();
                return Integer.parseInt(responseBody);
            } else {
                return -1;
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
            return -1;
        }
    }

    // handler to get the recurence patterns
    public List<String> getRecurrencePatterns() {
        String url = BASE_URL + "reminders/getRecurrencePatterns";
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                String responseBody = response.body().string();
                return parseRecurrencePatterns(responseBody);
            } else {
                return new ArrayList<>();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    // function to parse recurrence patterns into needed format
    private List<String> parseRecurrencePatterns(String responseBody) {
        List<String> recurrencePatterns = new ArrayList<>();
        String cleanResponseBody = responseBody.replaceAll("\\[", "").replaceAll("]", "").replaceAll("\"", "");
        String[] patterns = cleanResponseBody.split(",");
        for (String pattern : patterns) {
            recurrencePatterns.add(pattern.trim());
        }
        return recurrencePatterns;
    }
}





package org.example.budgetbuddyprojectfrontend;

import okhttp3.*;
import org.json.JSONObject;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

// defining class postRequestHandler to handle all posting requests
public class postRequestHandler {

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private final String BASE_URL = "http://localhost:8080/api/";
    OkHttpClient client = new OkHttpClient();

    // handler to add an expense for a user
    public Map<String, Object> addExpenseForUser(JSONObject requestBody) {
        Map<String, Object> responseBody = new HashMap<>();
        String url = BASE_URL + "expenses/add";

        RequestBody body = RequestBody.create(JSON, requestBody.toString());

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            System.out.println("Response from backend: " + response);
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected error: " + response.code());
            }

            String responseJson = response.body().string();

            JSONObject jsonResponse = new JSONObject(responseJson);
            responseBody.put("amount", jsonResponse.getDouble("amount"));
            responseBody.put("categoryName", jsonResponse.getString("categoryName"));

            return responseBody;
        } catch (IOException error) {
            System.err.println("Failed to add expense: " + error.getMessage());
            return responseBody;
        }
    }

    // handler to add an income for a user
    public Map<String, Object> addIncomeForUser(JSONObject requestBody) {
        Map<String, Object> responseBody = new HashMap<>();
        String url = BASE_URL + "incomes/add";

        RequestBody body = RequestBody.create(JSON, requestBody.toString());

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            System.out.println("Response from backend: " + response);
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected error: " + response.code());
            }

            String responseJson = response.body().string();

            JSONObject jsonResponse = new JSONObject(responseJson);
            responseBody.put("amount", jsonResponse.getDouble("amount"));
            responseBody.put("sourceName", jsonResponse.getString("sourceName"));

            return responseBody;
        } catch (IOException error) {
            System.err.println("Failed to add income: " + error.getMessage());
            return responseBody;
        }
    }

    // handler to add a reminder for a user
    public Map<String, Object> addReminder(JSONObject requestBody) {
        Map<String, Object> responseBody = new HashMap<>();
        String url = BASE_URL + "reminders/add";

        RequestBody body = RequestBody.create(JSON, requestBody.toString());

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected error: " + response.code());
            }

            String responseJson = response.body().string();

            JSONObject jsonResponse = new JSONObject(responseJson);
            responseBody.put("description", jsonResponse.getString("description"));
            responseBody.put("reminderDate", jsonResponse.getString("reminderDate"));
            responseBody.put("recurrencePatternStr", jsonResponse.getString("recurrencePatternStr"));
            responseBody.put("userId", jsonResponse.getInt("userId"));

            return responseBody;
        } catch (IOException error) {
            System.err.println("Failed to add reminder: " + error.getMessage());
            return responseBody;
        }
    }

    // handler to add a user
    public boolean addUser(String username, String password, String email) {
        String url = BASE_URL + "users/addUser";
        RequestBody body = new FormBody.Builder()
                .add("username", username)
                .add("password", password)
                .add("email", email)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                return true;
            } else {
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    // handler to login a user
    public boolean login(String username, String password) {
        String url = BASE_URL + "users/login";
        RequestBody body = new FormBody.Builder()
                .add("username", username)
                .add("password", password)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                String responseBody = response.body().string();
                return Boolean.parseBoolean(responseBody);
            } else {
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}





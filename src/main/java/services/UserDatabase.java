package services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserDatabase {

    
    private static final Path FILE_PATH = Paths.get("assets", "users.json");

    // Automatically populates dummy users if the JSON file is empty when accessed
    static {
        if (getAllUsers().isEmpty()) {
            registerUser("U001", "Faustine", "faustine", "pass123", "ADMIN");
            registerUser("U002", "Praise", "praise", "auth456", "ADMIN");
            registerUser("U003", "Travis", "travis", "movies789", "CUSTOMER");
            registerUser("U004", "Jampiere", "jampiere", "timer000", "CUSTOMER");
        }
    }

    
    public static boolean registerUser(String userId, String name, String username, String password, String role) {
        try {
            if (!Files.exists(FILE_PATH)) {
                if (FILE_PATH.getParent() != null) {
                    Files.createDirectories(FILE_PATH.getParent());
                }
                Files.writeString(FILE_PATH, "[]");
            }

            String content = Files.readString(FILE_PATH).trim();
            if (content.isEmpty()) {
                content = "[]";
            }

            // Prevent duplicate usernames
            if (content.contains("\"username\": \"" + username + "\"")) {
                System.out.println("Username already taken!");
                return false;
            }

            // Format the new user as a JSON object
            String newUserJson = String.format(
                "  {\n" +
                "    \"userId\": \"%s\",\n" +
                "    \"name\": \"%s\",\n" +
                "    \"username\": \"%s\",\n" +
                "    \"password\": \"%s\",\n" +
                "    \"role\": \"%s\"\n" +
                "  }",
                escapeJson(userId), escapeJson(name), escapeJson(username), escapeJson(password), escapeJson(role)
            );

            // Inserting the JSON object into the JSON array
            String updatedJson;
            if (content.equals("[]")) {
                updatedJson = "[\n" + newUserJson + "\n]";
            } else {
                int closingBracket = content.lastIndexOf("]");
                String existingUsers = content.substring(0, closingBracket).trim();
                updatedJson = existingUsers + ",\n" + newUserJson + "\n]";
            }

            Files.writeString(FILE_PATH, updatedJson);
            return true;

        } catch (IOException e) {
            System.out.println("Error saving user to JSON: " + e.getMessage());
            return false;
        }
    }

    // 2. Read all users from the JSON file (for Praise's AuthenticationService)
    public static List<Map<String, String>> getAllUsers() {
        List<Map<String, String>> usersList = new ArrayList<>();
        try {
            if (!Files.exists(FILE_PATH)) {
                return usersList;
            }
            String content = Files.readString(FILE_PATH).trim();
            if (content.equals("[]") || content.isEmpty()) {
                return usersList;
            }

            String body = content.substring(1, content.length() - 1).trim();
            String[] objects = body.split("\\},\\s*\\{");

            for (String obj : objects) {
                String cleanObj = obj.replace("{", "").replace("}", "").trim();
                Map<String, String> userMap = new HashMap<>();
                String[] pairs = cleanObj.split(",\\s*\n");

                for (String pair : pairs) {
                    String[] keyValue = pair.split(":", 2);
                    if (keyValue.length == 2) {
                        String key = keyValue[0].trim().replace("\"", "");
                        String value = keyValue[1].trim().replace("\"", "");
                        userMap.put(key, value);
                    }
                }
                usersList.add(userMap);
            }
        } catch (IOException e) {
            System.out.println("Error reading JSON file: " + e.getMessage());
        }
        return usersList;
    }

    private static String escapeJson(String text) {
        return text == null ? "" : text.replace("\"", "\\\"");
    }
}

package models;


public abstract class User {

    private final String userId;
    private String name;

    protected User(String userId, String name) {
        this.userId = requireNonBlank(userId, "ID");
        this.name = requireNonBlank(name, "Name");
    }

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = requireNonBlank(name, "Name");
    }

    /** Each subclass must say what kind of user it is. */
    public abstract String getRole();

    @Override
    public String toString() {
        return getRole() + "{id=" + userId + ", name=" + name + "}";
    }

    /** Shared validation helper, available to subclasses. */
    protected static String requireNonBlank(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " must not be blank.");
        }
        return value.trim();
    }
}
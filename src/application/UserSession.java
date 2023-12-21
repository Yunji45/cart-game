package application;

public class UserSession {
    private static int currentUserId;

    public static int getCurrentUserId() {
        return currentUserId;
    }

    public static void setCurrentUserId(int userId) {
        currentUserId = userId;
    }
}

/*package log;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserManageTest {
    private UserManage userManage;

    @BeforeEach
    void setUp() {
        // Use a temporary file for testing
        userManage = new UserManage();
    }

    @Test
    void testRegisterUser() {
        // Test registering a new user
        assertTrue(userManage.registerUser("newUser", "password"));
        
        // Test registering the same user again (should fail)
        //assertFalse(userManage.registerUser("newUser", "password"));
    }

    @Test
    void testLoginUser() {
        // Register a user for testing login
        userManage.registerUser("testUser", "testPassword");

        // Test login with correct credentials
        assertTrue(userManage.loginUser("testUser", "testPassword"));

        // Test login with incorrect password
        assertFalse(userManage.loginUser("testUser", "wrongPassword"));

        // Test login with non-existent username
        assertFalse(userManage.loginUser("nonexistentUser", "password"));
    }

    @Test
    void testFileOperations() {
        // Register a user to be saved to the file
        userManage.registerUser("fileUser", "filePassword");

        // Create a new UserManage instance to load users from the file
        UserManage newUserManage = new UserManage();

        // Test if the user was loaded from the file
        assertTrue(newUserManage.loginUser("fileUser", "filePassword"));
    }
}
*/
package teste;


package teste;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import log.Administrator;

public class AdministratorTest {
    private Administrator administrator;

    @Before
    public void setUp() {
        administrator = new Administrator("admin", "admin123");
    }

    @Test
    public void testGetUsername() {
        assertEquals("admin", administrator.getUsername());
    }

    @Test
    public void testSetUsername() {
        administrator.setUsername("newAdmin");
        assertEquals("newAdmin", administrator.getUsername());
    }

    @Test
    public void testGetPassword() {
        assertEquals("admin123", administrator.getPassword());
    }

    @Test
    public void testSetPassword() {
        administrator.setPassword("newPassword");
        assertEquals("newPassword", administrator.getPassword());
    }
}

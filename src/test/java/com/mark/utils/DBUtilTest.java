package com.mark.utils;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Author: Mark
 * Date  : 2015/2/6
 * Time  : 16:54
 */
public class DBUtilTest {

    private DBUtil util;

    @Before
    public void setup() {
        util = new DBUtil();
    }

    @Test
    public void testGetConnection() throws SQLException {
        Connection connection = util.getConnection();
        assertNotNull(connection);
    }

    @Test
    public void testRelease() throws SQLException {
        Connection connection = util.getConnection();
        util.release(connection, null, null);
        assertNotNull(connection);
    }

}

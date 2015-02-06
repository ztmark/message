package com.mark.service;

import com.mark.domain.Message;
import com.mark.service.MessageService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

/**
 * Author: Mark
 * Date  : 2015/2/6
 * Time  : 17:42
 */
public class MessageServiceTest {

    private MessageService service;

    @Before
    public void setup() {
        service = new MessageService();
    }


    @Test
    public void testInsert() throws SQLException, NoSuchAlgorithmException {
        service.insert("what the hell", "mark");
    }

    public void testSelect() throws SQLException {
        Message message = service.select("48a42077f2934596bb8cd491051d9a83","mark");
        Assert.assertNotNull(message);
    }

}

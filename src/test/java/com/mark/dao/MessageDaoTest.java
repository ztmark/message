package com.mark.dao;

import com.mark.domain.Message;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.UUID;

/**
 * Author: Mark
 * Date  : 2015/2/6
 * Time  : 16:59
 */
public class MessageDaoTest {

    private MessageDao dao;

    @Before
    public void setup() {
        dao = new MessageDao();
    }

    @Test
    public void testInsert() throws SQLException {
        Message message = new Message();
        String uid = UUID.randomUUID().toString().replaceAll("-", "");
        message.setUuid(uid);
        message.setText("people believe what they want to believe");
        message.setPassword("lskdjflaksdjflkasjdlfjlaksdjf");
        message.setExpire((int) (System.currentTimeMillis()/1000L+360));
        dao.insert(message);
    }

    @Test
    public void testSelect() throws SQLException {
        Message message = dao.select("55c4d83d221c4f6bab5d91786137ae39","lskdjflaksdjflkasjdlfjlaksdjf");
        assertNotNull(message);
        assertEquals(message.getText(),"people believe what they want to believe");
    }

    @Test
    public void testSelectNone() throws SQLException {
        Message message = dao.select("asdfasdfasdfsadfsdfasdf","asdfadfa");
        assertNull(message);
    }

}

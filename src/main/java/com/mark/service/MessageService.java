package com.mark.service;

import com.mark.dao.MessageDao;
import com.mark.domain.Message;

import java.sql.SQLException;
import java.util.Random;
import java.util.UUID;

/**
 * Author: Mark
 * Date  : 2015/2/5
 * Time  : 21:05
 */
public class MessageService {

    private MessageDao dao = new MessageDao();
    private static final String CHARS = "1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public Message insert(String text, String duration) {
        String uid = UUID.randomUUID().toString().replaceAll("-", "");
        int expire =Integer.valueOf(duration) * 60;
        Message message = new Message();
        try {
            message.setUuid(uid);
            message.setText(text);
            message.setExpire(expire);
            message.setPassword(randomPwd());

            dao.insert(message);
        } catch (SQLException e) {
            return null;
        }
        return message;
    }

    private String randomPwd() {
        StringBuilder sb = new StringBuilder(4);
        Random random = new Random(System.currentTimeMillis());
        for (int i = 0; i < 4; i++) {
            sb.append(CHARS.charAt(random.nextInt(CHARS.length())));
        }
        return sb.toString();
    }

    public Message select(String uuid, String password) throws SQLException {
        return dao.select(uuid, password);
    }

    public void delete(String uuid) {
        try {
            dao.delete(uuid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}

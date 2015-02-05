package com.mark.service;

import com.mark.dao.MessageDao;
import com.mark.domain.Message;
import com.sun.org.apache.xerces.internal.impl.dv.util.HexBin;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.UUID;

/**
 * Author: Mark
 * Date  : 2015/2/5
 * Time  : 21:05
 */
public class MessageService {

    private MessageDao dao = new MessageDao();

    public void insert(String text, String password) throws NoSuchAlgorithmException, SQLException {
        String uid = UUID.randomUUID().toString().replaceAll("-", "");
        int expire = (int) (System.currentTimeMillis() / 1000L + 15 * 60);
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(text.getBytes());

        Message message = new Message();
        message.setUuid(uid);
        message.setText(text);
        message.setExpire(expire);
        message.setPassword(HexBin.encode(md.digest()));

        dao.insert(message);
    }

    public Message select(String uuid, String password) throws SQLException {
        return dao.select(uuid, password);
    }

}

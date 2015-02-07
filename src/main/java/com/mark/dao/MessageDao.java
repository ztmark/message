package com.mark.dao;

import com.mark.domain.Message;
import com.mark.utils.DBUtil;
import com.sun.org.apache.xerces.internal.impl.dv.util.HexBin;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Author: Mark
 * Date  : 2015/2/5
 * Time  : 20:07
 */
public class MessageDao {

    private DBUtil util = new DBUtil();

    public void insert(Message message) throws SQLException {
        String sql = "insert into message(uuid,text,expire,password) values(?,?,?,?)";
        Connection conn = util.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1,message.getUuid());
        ps.setString(2,message.getText());
        ps.setInt(3, (int) (message.getExpire() + System.currentTimeMillis()));
        ps.setString(4,encodePwd(message.getPassword()));
        ps.execute();
        util.release(conn,ps,null);
    }

    public Message select(String uuid, String password) throws SQLException {
        Connection conn = util.getConnection();
        String sql = "select text,expire from message where uuid=? AND password=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, uuid);
        ps.setString(2, encodePwd(password));
        ResultSet rs = ps.executeQuery();
        Message ans = null;
        if (rs.next()) {
            ans = new Message();
            ans.setUuid(uuid);
            int now = (int) (System.currentTimeMillis() / 1000L);
            int expire = rs.getInt("expire") - now;
            ans.setExpire(expire);
            ans.setText(rs.getString("text"));
            ans.setPassword(password);
        }
        util.release(conn,ps,rs);

        if (ans == null || ans.getExpire() <= 0) {
            return null;
        }
        return ans;
    }

    public void delete(String uuid) throws SQLException {
        Connection connection = util.getConnection();
        String sql = "delete from message where uuid=?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, uuid);
        ps.execute();
    }

    private String encodePwd(String pwd) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(pwd.getBytes());
            return HexBin.encode(md.digest());
        } catch (NoSuchAlgorithmException e) {
            return pwd;
        }
    }

}

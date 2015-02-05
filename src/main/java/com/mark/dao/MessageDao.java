package com.mark.dao;

import com.mark.domain.Message;
import com.mark.utils.DBUtil;

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
        ps.setInt(3,message.getExpire());
        ps.setString(4,message.getPassword());
        ps.execute();
        util.release(conn,ps,null);
    }

    public Message select(String uuid, String password) throws SQLException {
        Connection conn = util.getConnection();
        String sql = "select text,expire from message where uuid=? AND password=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, uuid);
        ps.setString(2, password);
        ResultSet rs = ps.executeQuery();
        Message ans = null;
        if (rs.next()) {
            ans = new Message();
            ans.setUuid(uuid);
            ans.setExpire(rs.getInt("expire"));
            ans.setText(rs.getString("text"));
            ans.setPassword(password);
        }
        util.release(conn,ps,rs);
        int now = (int) (System.currentTimeMillis() / 1000L);
        if (ans == null || now > ans.getExpire()) {
            return null;
        }
        return ans;
    }

}

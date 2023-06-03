package org.example.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JDBCInsertTest {
    public static void main(String[] args) {
        Connection conn = null;
        PreparedStatement pstat = null;

        try {
            Class.forName("com.mysql.jdbc.Driver");

            String url = "jdbc:mysql://127.0.0.1:3306/text_board?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull";
            //DB => text_board
            conn = DriverManager.getConnection(url, "root", "");

            String sql = "INSERT INTO article";
            sql += " SET regDate=NOW()";
            sql += ", updateDate=NOW()";
            sql += ", title = CONCAT(\"제목1\", RAND())";
            sql += ", content = CONCAT(\"내용1\", RAND());";

            pstat = conn.prepareStatement(sql);
            int affectRows = pstat.executeUpdate();

            System.out.println("sql: " + sql);
        } catch (ClassNotFoundException e) {
            System.out.println("드라이버 로딩 실패");
        } catch (SQLException e) {
            System.out.println("에러: " + e);
        } finally {
            try {
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (pstat != null && !pstat.isClosed()) {
                    pstat.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

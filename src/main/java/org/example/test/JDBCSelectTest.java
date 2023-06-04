package org.example.test;

import org.example.Article;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JDBCSelectTest {
  public static void main(String[] args) {
    Connection conn = null;
    PreparedStatement pstat = null;
    ResultSet rs = null;

    List<Article> articles = new ArrayList<>();

    try {
      Class.forName("com.mysql.jdbc.Driver");

      String url = "jdbc:mysql://127.0.0.1:3306/text_board?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull";
      //DB => text_board
      conn = DriverManager.getConnection(url, "root", "");

      String sql = "SELECT *";
      sql += " FROM article";
      sql += " ORDER BY id DESC;";

      pstat = conn.prepareStatement(sql);
      rs = pstat.executeQuery(sql);

      while(rs.next()) {
        int id = rs.getInt("id");
        String regDate = rs.getString("regDate");
        String updateDate = rs.getString("updateDate");
        String title = rs.getString("title");
        String content = rs.getString("content");

        /*
        Article article = new Article(id, regDate, updateDate, title, content);
        articles.add(article);
        //Arraylist 객체인 articles에서 객체 article을 추가로 선택한다.
        */
      }

      System.out.println("articles : " + articles);

      System.out.println("rs : " + rs.toString());
    } catch (ClassNotFoundException e) {
      System.out.println("드라이버 로딩 실패");
    } catch (SQLException e) {
      System.out.println("에러: " + e);
    } finally {
      try {
        if (rs != null && !rs.isClosed()) {
          rs.close();
        }
      } catch (SQLException e) {
        e.printStackTrace();
      }

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

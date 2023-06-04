
//JDBC 텍스트 게시판 프로젝트의 기본 logic은 App 클래스 안에 작성한다.

package org.example;

import org.example.Container.Container;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {
    public void run(){
        Scanner sc = Container.scanner;
        List<Article> articles = new ArrayList<>();
        int articleLastId = 0; //게시물 번호

        while (true){
            System.out.printf("명령어) ");
            String cmd = sc.nextLine();

            //게시물 작성
            if(cmd.equals("/usr/article/write")){
                System.out.println("== 게시물 등록 ==");
                System.out.printf("제목: ");
                String title = sc.nextLine();
                System.out.printf("내용: ");
                String content = sc.nextLine();

                int id = ++articleLastId;

                //게시물을 작성하면 db에 저장한다.
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
                    sql += ", title =  \"" + title + "\""; //title (제목)
                    sql += ", content =  \"" + content + "\""; //content (내용)

                    pstat = conn.prepareStatement(sql);
                    int affectRows = pstat.executeUpdate();

                    System.out.println("affectRows : " + affectRows);
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

                Article article = new Article(id, title, content);
                System.out.println("생성된 게시물 객체: " + article);

                articles.add(article);
                System.out.printf("%d번 게시물이 등록되었습니다.\n", article.id);
            }

            //게시물 조회
            else if (cmd.equals("/usr/article/list")) {
                System.out.println("== 게시물 리스트 ==");

                if(articles.isEmpty()){
                    System.out.println("게시물이 존재하지 않습니다.");
                    continue;
                }

                System.out.println("번호 / 제목");

                for(Article article: articles){
                    System.out.printf("%d / %s\n", article.id, article.title);
                }
            }

            //프로그램 종료
            else if (cmd.equals("exit")) {
                System.out.println("프로그램을 종료합니다.");
                break;
                //exit를 입력 시 while 반복문을 빠져나온다.
            }

            //잘못된 명령어 입력 시
            else {
                System.out.println("잘못된 명령어를 입력하셨습니다.");
                System.out.println("명령어를 확인해주세요.");
            }
        }
        sc.close();
        //exit를 입력해서 while 반복문을 빠져나오면 Scanner 입력 받기를 종료한다.
    }
}

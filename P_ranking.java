package Puzzle;

import java.awt.Button;
import java.awt.Color;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class P_ranking extends JFrame implements ActionListener {

	// DB접근하기 위한 드라이버, URL
	String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	String url = "jdbc:ucanaccess://D:\\android\\projects\\Project_Puzzle\\src\\Puzzle\\RankingDB.accdb";
	Connection con = null;		// DB
	Statement stmt = null;		// DB
	ResultSet rs = null;		// SQL 실행 결과 저장
	String sql = null;			// SQL 문
	long m_Timer;				// 걸린 시간
	int m_Count;				// 클릭 횟수
	String m_rank;				// 등 수 
	boolean check = false;		// 신기록 달성 플래그
	TextField inputName = new TextField();	// 신기록 달성시 이름을 입력받는 TextField

	// 랭킹을 표시할 프레임 생성
	public void Create_ranking (long Timer, int M_count){
		setLayout(null);
		// 초기화
		m_Timer = Timer;
		m_Count = M_count;
		m_rank = null;
		
		// UI
		setSize(300,240);
		setTitle("순위표");
		setBackground(Color.white);
		
		Label la1 = new Label("Puzzle Ranking");
		Label la2 = new Label("순위");
		Label la3 = new Label("이름");
		Label la4 = new Label("시간(초)");
		Label la5 = new Label("클릭 수(번)");
		Label R[] = new Label[5];	// 순위
		Label N[] = new Label[5];	// 이름
		Label T[] = new Label[5];	// 시간
		Label C[] = new Label[5];	// 클릭수
		Button B_check = new Button("확인");
		B_check.addActionListener(this);
		
		la1.setLocation(100, 0);	// Puzzle Ranking
		la1.setSize(100, 30);
		la2.setLocation(10, 30);	// 순위
		la2.setSize(30, 20);
		la3.setLocation(70, 30);	// 이름
		la3.setSize(30, 20);
		la4.setLocation(130, 30);	// 시간(초)
		la4.setSize(50, 20);
		la5.setLocation(200, 30);	// 클릭 수(번)
		la5.setSize(70, 20);
		B_check.setLocation(110, 160);	// 버튼
		B_check.setSize(60,30);
		
		add(la1);
		add(la2);
		add(la3);
		add(la4);
		add(la5);
		add(B_check);
		
		// DB
		try{
			Class.forName(driver);
			con = DriverManager.getConnection(url);
			sql = "Select * From Ranking";
			stmt = con.createStatement();
			System.out.println("DB 연결 성공");
			rs = stmt.executeQuery(sql);
			
			// 랭킹 출력
			while (rs.next()){
				for (int a=0 ; a<5 ; a++){
					// 시간이 DB값보다 작을 때(신기록)
					if (m_Timer < Long.parseLong(rs.getString("시간")) && check == false){
						R[a] = new Label(rs.getString("순위"));
						m_rank = rs.getString("순위");
						// 순위를 저장하고 Label 대신 TextField를 표시
						inputName.setSize(45,20);
						inputName.setLocation(60, 50 + a * 20);
						T[a] = new Label(Long.toString(m_Timer));
						C[a] = new Label(Integer.toString(m_Count));
						add(inputName);
						check = true;	// 신기록 달성 플래그
					}
					else{	// 신기록이 아닐 때 기존 순위 출력
						R[a] = new Label(rs.getString("순위"));
						N[a] = new Label(rs.getString("이름"));
						T[a] = new Label(rs.getString("시간"));
						C[a] = new Label(rs.getString("클릭"));
						N[a].setLocation(60, 50 + a * 20);
						N[a].setSize(40, 20);
						add(N[a]);
					}
					R[a].setLocation(15, 50 + a * 20);
					R[a].setSize(30, 20);
					T[a].setLocation(140, 50 + a * 20);
					T[a].setSize(30, 20);
					C[a].setLocation(210, 50 + a * 20);
					C[a].setSize(30, 20);
					add(R[a]);
					add(T[a]);
					add(C[a]);
					rs.next();
				}
			}
			rs.close();	
		}catch(Exception e){
			System.out.println(e);
		}
		setVisible(true);	// paint() 수행
	}	// Create_ranking

	@Override	// 확인 버튼 클릭
	public void actionPerformed(ActionEvent e) {
		// 신기록이 존재하고 TextField에 입력값이 있을 때
		if (check == true && inputName.getText().length() != 0){
			try{	// DB 갱신
				System.out.println("기록이 저장되었습니다.");
				String m_sql = "Update ranking SET 이름 = ?, 시간 = ?, 클릭 = ? Where 순위 = ?";
				PreparedStatement pstmt = con.prepareStatement(m_sql);
				pstmt.setString(1, inputName.getText());
				pstmt.setString(2, Long.toString(m_Timer));
				pstmt.setString(3, Integer.toString(m_Count));
				pstmt.setString(4, m_rank);
				pstmt.executeUpdate();
				pstmt.close();
			}catch(Exception e1) {
				System.out.println(e1);
			}
		}	
		// 데이터베이스 연결 해제
		try{
			System.out.println("DB 연결 해제");
			if(con != null)
				con.close();
			if(stmt != null)
				stmt.close();
		}catch(Exception e2){
			System.out.println(e);
		}
		System.exit(0);
	}		// actionPerformed
}

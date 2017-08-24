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

	// DB�����ϱ� ���� ����̹�, URL
	String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	String url = "jdbc:ucanaccess://D:\\android\\projects\\Project_Puzzle\\src\\Puzzle\\RankingDB.accdb";
	Connection con = null;		// DB
	Statement stmt = null;		// DB
	ResultSet rs = null;		// SQL ���� ��� ����
	String sql = null;			// SQL ��
	long m_Timer;				// �ɸ� �ð�
	int m_Count;				// Ŭ�� Ƚ��
	String m_rank;				// �� �� 
	boolean check = false;		// �ű�� �޼� �÷���
	TextField inputName = new TextField();	// �ű�� �޼��� �̸��� �Է¹޴� TextField

	// ��ŷ�� ǥ���� ������ ����
	public void Create_ranking (long Timer, int M_count){
		setLayout(null);
		// �ʱ�ȭ
		m_Timer = Timer;
		m_Count = M_count;
		m_rank = null;
		
		// UI
		setSize(300,240);
		setTitle("����ǥ");
		setBackground(Color.white);
		
		Label la1 = new Label("Puzzle Ranking");
		Label la2 = new Label("����");
		Label la3 = new Label("�̸�");
		Label la4 = new Label("�ð�(��)");
		Label la5 = new Label("Ŭ�� ��(��)");
		Label R[] = new Label[5];	// ����
		Label N[] = new Label[5];	// �̸�
		Label T[] = new Label[5];	// �ð�
		Label C[] = new Label[5];	// Ŭ����
		Button B_check = new Button("Ȯ��");
		B_check.addActionListener(this);
		
		la1.setLocation(100, 0);	// Puzzle Ranking
		la1.setSize(100, 30);
		la2.setLocation(10, 30);	// ����
		la2.setSize(30, 20);
		la3.setLocation(70, 30);	// �̸�
		la3.setSize(30, 20);
		la4.setLocation(130, 30);	// �ð�(��)
		la4.setSize(50, 20);
		la5.setLocation(200, 30);	// Ŭ�� ��(��)
		la5.setSize(70, 20);
		B_check.setLocation(110, 160);	// ��ư
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
			System.out.println("DB ���� ����");
			rs = stmt.executeQuery(sql);
			
			// ��ŷ ���
			while (rs.next()){
				for (int a=0 ; a<5 ; a++){
					// �ð��� DB������ ���� ��(�ű��)
					if (m_Timer < Long.parseLong(rs.getString("�ð�")) && check == false){
						R[a] = new Label(rs.getString("����"));
						m_rank = rs.getString("����");
						// ������ �����ϰ� Label ��� TextField�� ǥ��
						inputName.setSize(45,20);
						inputName.setLocation(60, 50 + a * 20);
						T[a] = new Label(Long.toString(m_Timer));
						C[a] = new Label(Integer.toString(m_Count));
						add(inputName);
						check = true;	// �ű�� �޼� �÷���
					}
					else{	// �ű���� �ƴ� �� ���� ���� ���
						R[a] = new Label(rs.getString("����"));
						N[a] = new Label(rs.getString("�̸�"));
						T[a] = new Label(rs.getString("�ð�"));
						C[a] = new Label(rs.getString("Ŭ��"));
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
		setVisible(true);	// paint() ����
	}	// Create_ranking

	@Override	// Ȯ�� ��ư Ŭ��
	public void actionPerformed(ActionEvent e) {
		// �ű���� �����ϰ� TextField�� �Է°��� ���� ��
		if (check == true && inputName.getText().length() != 0){
			try{	// DB ����
				System.out.println("����� ����Ǿ����ϴ�.");
				String m_sql = "Update ranking SET �̸� = ?, �ð� = ?, Ŭ�� = ? Where ���� = ?";
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
		// �����ͺ��̽� ���� ����
		try{
			System.out.println("DB ���� ����");
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

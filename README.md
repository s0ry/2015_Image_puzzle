# 2015 이미지 퍼즐 게임

개요
-------------
9(3*3)조각으로 나뉘어진 이미지를 움직여 맞추는 퍼즐 게임입니다.
마우스를 클릭하여 조각을 이동시킬 수 있습니다.
Play time과 클릭 횟수에 따라 데이터베이스에 랭킹으로 저장됩니다.
순위권 성적이면 데이터베이스에 이름을 입력할 수 있습니다.

+ JAVA
+ 이미지 처리 및 게임 진행 프로세스
+ JDBC를 이용한 DB연동 

클래스 설계
-------------
+ P_frame.java
<pre><code>
public BufferedImage ori_img;		// 원본 이미지
public BufferedImage[][] sub_img = new BufferedImage[3][3];	// 분할 이미지 버퍼
public int[][] sub_index = new int[3][3];			// 분할 이미지 번호
P_ranking Pranking = new P_ranking();				// P_ranking 객체
Graphics g;				// paint()를 수행할 Graphics 객체	
int B_row, B_col;			// 빈 칸의 인덱스 
int M_count;				// 조각 옮긴 수
long S_Timer;				// 시작 시간
public long Timer;			// 걸린 시간

public void Create_Frame(){		// 프레임 생성
public void Create_Image(){		// 이미지 생성
public void Sub_Image(int m_piece){	// 이미지 분할 셋팅(조각 수)
public int Random_Setting(){		// 난수 반환
public void paint(Graphics g){		// Swing 의 JFrame을 상속한 클래스에서 paint()메소드를 오버라이드
public boolean Correct_clicked(int P_index){		// 클릭한 조각의 이동 가능 유무 반환(조각 위치 인덱스)
public BufferedImage Make_White(){			// 빈 칸 이미지 반환
public void Change_Image(int x, int y, int index){	// 조각 이미지 교환(이동)처리 함수(2차원 배열 x, 2차원 배열 y, 조각 위치 인덱스)
public boolean Check_Image(){			// 조각 맞추기 완성 체크
public void End_Game(){				// 게임 클리어
public void mousePressed(MouseEvent e) {	// 마우스 클릭 이벤트 처리
</code></pre>

+ P_ranking.java
<pre><code>
// DB접근하기 위한 드라이버, URL
String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
String url = "jdbc:ucanaccess://D:\\android\\projects\\Project_Puzzle\\src\\Puzzle\\RankingDB.accdb";
Connection con = null;		// DB
Statement stmt = null;		// DB
ResultSet rs = null;		// SQL 실행 결과 저장
String sql = null;		// SQL 문
long m_Timer;			// 걸린 시간
int m_Count;			// 클릭 횟수
String m_rank;			// 등 수 
boolean check = false;		// 신기록 달성 플래그
TextField inputName = new TextField();	// 신기록 달성시 이름을 입력받는 TextField

public void Create_ranking (long Timer, int M_count){	// 랭킹을 표시할 프레임 생성
public void actionPerformed(ActionEvent e) {		// 확인 버튼 클릭(데이터 베이스 연결 해제)
</code></pre>

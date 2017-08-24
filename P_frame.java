package Puzzle;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

// ������ ������ �̹��� �ҷ����� �� �ɰ���

// GUI Swing
@SuppressWarnings("serial")
public class P_frame extends JFrame implements MouseListener{
	
	public BufferedImage ori_img;	// ���� �̹���
	public BufferedImage[][] sub_img = new BufferedImage[3][3];	// ���� �̹��� ����
	public int[][] sub_index = new int[3][3];					// ���� �̹��� ��ȣ
	P_ranking Pranking = new P_ranking();						// P_ranking ��ü
	Graphics g;					// paint()�� ������ Graphics ��ü	
	int B_row, B_col;			// �� ĭ�� �ε��� 
	int M_count;				// ���� �ű� ��
	long S_Timer;				// ���� �ð�
	public long Timer;			// �ɸ� �ð�
	
	// ������ ����
	public void Create_Frame(){
		setLayout(null);
		
		// UI
		setSize(600,400);
		setTitle("�׸� ���߱� ����");
		setBackground(Color.white);
		
		Create_Image();		// �̹��� ���� �޼ҵ� ȣ��
		Sub_Image(9);		// �̹��� ���� �޼ҵ� ȣ��
		M_count = 200;		// ���� �ű� �� �ʱ�ȭ
		Timer = 200;
		S_Timer = System.currentTimeMillis();	// ���� �ð� ����
		addMouseListener(this);		// MouseListener ����
		setVisible(true);	// paint() ����
		
		// â���� �̺�Ʈ(���μ��� ���� ����)
		addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent we){
				System.exit(0);
			}
		});
		// �׽�Ʈ
		//Pranking.Create_ranking(Timer, M_count);
	}
	
	// �̹��� ����
	public void Create_Image(){
		try {
			ori_img = ImageIO.read(new File("testPark.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// �̹��� ���� ����(���� ��)
	public void Sub_Image(int m_piece){
		int width = ori_img.getWidth(this);		// ���κ��ұ���
		int height = ori_img.getHeight(this);	// ���κ��ұ���
		int R_number[] = new int[9];			// ���� ���� �迭
		int number = 0;							// ���� ���� ����
		boolean compare;						// ���� ��
		int row, col;							// ���� �̹��� ���� �ε���
		int R_index = 0;						// ���� �迭 �ε���
		
		if (m_piece == 9){			// 9����
			
			for (int i=0 ; i<9 ; i++){	// ���� ���� �迭 �ʱ�ȭ		
				R_number[i] = 0;
			}
			R_number[0] = Random_Setting();	// ���� ����
			for (int j=1 ; j<9 ; j++){	// ���� ���� �迭�� 1~9�� ���� �ٸ� �� ����
				compare = true;
				number = Random_Setting();
				for (int z=0 ; z<9 ; z++){
					if (R_number[z] == number){
						compare = false;
					}
				}
				if (compare == true){
					R_number[j] = number;
				}
				else{
					j--;
				}
			}
			width /= 3;
			height /= 3;
			
			for (row=0 ; row<3 ; row++){	// ���� ���� �迭���� ���� ���� ���� �̹��� ����
				for (col=0 ; col<3 ; col++){
					switch (R_number[R_index]){
					case 1:
						sub_img[row][col] = ori_img.getSubimage(0,  0, width, height);
						sub_index[row][col] = 1;
						break;
					case 2:
						sub_img[row][col] = ori_img.getSubimage(width,  0, width, height);
						sub_index[row][col] = 2;
						break;
					case 3:
						sub_img[row][col] = ori_img.getSubimage(width * 2,  0, width, height);
						sub_index[row][col] = 3;
						break;
					case 4:
						sub_img[row][col] = ori_img.getSubimage(0,  height, width, height);
						sub_index[row][col] = 4;
						break;
					case 5:
						sub_img[row][col] = ori_img.getSubimage(width,  height, width, height);
						sub_index[row][col] = 5;
						break;
					case 6:
						sub_img[row][col] = ori_img.getSubimage(width * 2,  height, width, height);
						sub_index[row][col] = 6;
						break;
					case 7:
						sub_img[row][col] = ori_img.getSubimage(0,  height * 2, width, height);
						sub_index[row][col] = 7;
						break;
					case 8:
						sub_img[row][col] = ori_img.getSubimage(width,  height * 2, width, height);
						sub_index[row][col] = 8;
						break;
					case 9:		// ���� ���߱⸦ ���� �� ĭ 
						sub_index[row][col] = 9;
						B_row = row;
						B_col = col;
						break;
					default:
						System.out.println("���� �� ���� ���� ����");
						break;
					}
					R_index++;
				}
			}
		}
	}	// Sub_Image
	
	// ���� ��ȯ
	public int Random_Setting(){
		int number = (int)(Math.random()*9 + 1);	// 1~9���� ����
		return number;
	}
	
	// Swing �� JFrame�� ����� Ŭ�������� paint()�޼ҵ带 �������̵�
	// paint()�� setVisible()�� ������ �� �����
	public void paint(Graphics g){
		if (ori_img != null){
			// drawImage(��ü, x ��ǥ, y ��ǥ, width, height, Observer)
			g.drawImage(ori_img, 30, 50, 100, 100, this);
			g.drawImage(sub_img[0][0], 180, 50, 100, 100, this);
			g.drawImage(sub_img[0][1], 280, 50, 100, 100, this);
			g.drawImage(sub_img[0][2], 380, 50, 100, 100, this);
			g.drawImage(sub_img[1][0], 180, 150, 100, 100, this);
			g.drawImage(sub_img[1][1], 280, 150, 100, 100, this);
			g.drawImage(sub_img[1][2], 380, 150, 100, 100, this);
			g.drawImage(sub_img[2][0], 180, 250, 100, 100, this);
			g.drawImage(sub_img[2][1], 280, 250, 100, 100, this);
			g.drawImage(sub_img[2][2], 380, 250, 100, 100, this);
		}
		else{
			System.out.println("�̹��� �ε� ����");
		}
	}

	// Ŭ���� ������ �̵� ���� ���� ��ȯ(���� ��ġ �ε���)
	public boolean Correct_clicked(int P_index){
		switch (P_index){
		case 1:
			if (sub_index[0][1] == 9 || sub_index[1][0] == 9){
				return true;
			}
			break;
		case 2:
			if (sub_index[0][0] == 9 || sub_index[0][2] == 9 || sub_index[1][1] == 9){
				return true;
			}
			break;
		case 3:
			if (sub_index[0][1] == 9 || sub_index[1][2] == 9){
				return true;
			}
			break;
		case 4:
			if (sub_index[0][0] == 9 || sub_index[1][1] == 9 || sub_index[2][0] == 9){
				return true;
			}
			break;
		case 5:
			if (sub_index[0][1] == 9 || sub_index[1][0] == 9 || sub_index[1][2] == 9 || sub_index[2][1] == 9){
				return true;
			}
			break;
		case 6:
			if (sub_index[0][2] == 9 || sub_index[1][1] == 9 || sub_index[2][2] == 9){
				return true;
			}
			break;
		case 7:
			if (sub_index[1][0] == 9 || sub_index[2][1] == 9){
				return true;
			}
			break;
		case 8:
			if (sub_index[1][1] == 9 || sub_index[2][0] == 9 || sub_index[2][2] == 9){
				return true;
			}
			break;
		case 9:
			if (sub_index[1][2] == 9 || sub_index[2][1] == 9){
				return true;
			}
			break;
		default:
			System.out.println("���� �迭 ����");
			break;
		}
		return false;
	}
	
	// �� ĭ �̹��� ��ȯ
	public BufferedImage Make_White(){
		BufferedImage m_white = null;
		try {
			m_white = ImageIO.read(new File("white.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return m_white;
	}
	
	// ���� �̹��� ��ȯ(�̵�)ó�� �Լ�(2���� �迭 x, 2���� �迭 y, ���� ��ġ �ε���)
	public void Change_Image(int x, int y, int index){
		if (Correct_clicked(index)){	// Ŭ���� ������ ������ ���� �� ĭ�� ���� ���
			sub_img[B_row][B_col] = sub_img[x][y];	
			sub_img[x][y] = Make_White();	// �̹��� �̵� �� �� ĭ �̹��� ����
			sub_index[B_row][B_col] = sub_index[x][y];
			sub_index[x][y] = 9;			// �ε��� ��ȯ
			B_row = x;				
			B_col = y;						// �� ĭ �ε��� ����
			M_count++;						// ���� �ű� �� + 1
		}
	}
	
	// ���� ���߱� �ϼ� üũ
	public boolean Check_Image(){
		if (sub_index[0][0] == 1 && sub_index[0][1] == 2 && sub_index[0][2] == 3 &&
				sub_index[1][0] == 4 && sub_index[1][1] == 5 && sub_index[1][2] == 6 &&
				sub_index[2][0] == 7 && sub_index[2][1] == 8 && sub_index[2][2] == 9){
			return true;
		}
		else{
			return false;
		}
	}
	
	// ���� Ŭ����
	public void End_Game(){
		int width = ori_img.getWidth(this)/3;		// ���κ��ұ���
		int height = ori_img.getHeight(this)/3;	// ���κ��ұ���
		sub_img[2][2] = ori_img.getSubimage(width * 2,  height * 2, width, height);
		repaint();		// �׸� �ϼ�
		// MessageDialog
		long E_Timer = System.currentTimeMillis();
		Timer = (E_Timer - S_Timer) / 1000;
		JOptionPane.showMessageDialog(this, "Ŭ�� �� : " + M_count + "��, �ɸ� �ð� : " + Timer + "��",
			"Complete Message", JOptionPane.PLAIN_MESSAGE);
		Pranking.Create_ranking(Timer, M_count);
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	// ���콺 Ŭ�� �̺�Ʈ ó��
	@Override
	public void mousePressed(MouseEvent e) {
		int inX = e.getX();		// ���콺 x ��ǥ
		int inY = e.getY();		// ���콺 y ��ǥ
		if (inX > 180 && inX < 280 && inY > 50 && inY < 150){		// [0][0]
			Change_Image(0,0,1);	// ���� �̹��� ��ȯ ó�� �Լ� 
		}
		else if(inX > 280 && inX < 380 && inY > 50 && inY < 150){	// [0][1]
			Change_Image(0,1,2);	// ���� �̹��� ��ȯ ó�� �Լ�
		}
		else if(inX > 380 && inX < 480 && inY > 50 && inY < 150){	// [0][2]
			Change_Image(0,2,3);	// ���� �̹��� ��ȯ ó�� �Լ�
		}
		else if(inX > 180 && inX < 280 && inY > 150 && inY < 250){	// [1][0]
			Change_Image(1,0,4);	// ���� �̹��� ��ȯ ó�� �Լ�
		}
		else if(inX > 280 && inX < 380 && inY > 150 && inY < 250){	// [1][1]
			Change_Image(1,1,5);	// ���� �̹��� ��ȯ ó�� �Լ�
		}
		else if(inX > 380 && inX < 480 && inY > 150 && inY < 250){	// [1][2]
			Change_Image(1,2,6);	// ���� �̹��� ��ȯ ó�� �Լ�
		}
		else if(inX > 180 && inX < 280 && inY > 250 && inY < 350){	// [2][0]
			Change_Image(2,0,7);	// ���� �̹��� ��ȯ ó�� �Լ�
		}
		else if(inX > 280 && inX < 380 && inY > 250 && inY < 350){	// [2][1]
			Change_Image(2,1,8);	// ���� �̹��� ��ȯ ó�� �Լ�
		}
		else if(inX > 380 && inX < 480 && inY > 250 && inY < 350){	// [2][2]
			Change_Image(2,2,9);	// ���� �̹��� ��ȯ ó�� �Լ�
		}
		repaint();			// paint()�޼ҵ� ����
		if (Check_Image()){	// ���� �̹��� �ϼ� üũ
			End_Game();
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}

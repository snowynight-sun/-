package view;
import controller.Ruler;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.sql.SQLException;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.Tetromino;
import model.Users;
import view.Tetris;

import model.Cell;

public class Tetris extends JPanel{
	protected static int id=0;
	private static JLabel message;
	protected static Tetromino nextone;
	protected static Tetromino tetromino;
	protected static final int ROWS = 20;
	protected static final int COLS = 10;
	protected static int score = 0;
	
	protected static String account = null;
	protected static int level = 5;
	protected static Cell[][] wall = new Cell[ROWS][COLS];
	private boolean STATE = true;//����״̬
	public static final int CELL_SIZE = 26;//���Ȼ���
	public static BufferedImage Z;
	public static BufferedImage S;
	public static BufferedImage J;
	public static BufferedImage L;
	public static BufferedImage O;
	public static BufferedImage I;
	public static BufferedImage T;
	public static BufferedImage bg;
	public static BufferedImage gameover;
	static {//��ȡ��̬��Դ
		try {
			Z = ImageIO.read(Tetris.class.getResource("Z.png"));
			S = ImageIO.read(Tetris.class.getResource("S.png"));
			J = ImageIO.read(Tetris.class.getResource("J.png"));
			T = ImageIO.read(Tetris.class.getResource("T.png"));
			O = ImageIO.read(Tetris.class.getResource("O.png"));
			I = ImageIO.read(Tetris.class.getResource("I.png"));
			L = ImageIO.read(Tetris.class.getResource("L.png"));
			bg= ImageIO.read(Tetris.class.getResource("bg.png"));
			gameover = ImageIO.read(Tetris.class.getResource("gameover.png"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	public static void startTetris() {//������
		JFrame frame = new JFrame();
		
		Tetris tetris = new Tetris();
		frame.add(tetris);
		frame.setSize(525, 600);
		frame.setLocationRelativeTo(null);//������������Ļ������
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//�û��ڴ˴����Ϸ��� "close" ʱĬ��ִ�еĲ���
		frame.setVisible(true);
		
		tetris.action();
		
	}
	public void action() {
		tetromino = Tetromino.ranShape();//��ǰ��Ϸ���
		nextone = Tetromino.ranShape();//Ԥ������һ������
		
		KeyListener kl = new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				int k = e.getKeyCode();
				keyMoveAction(k);
				repaint();
			}
		};
		this.addKeyListener(kl);//��Ӽ��̼�����
		this.setFocusable(true);//��ȡ����
		this.requestFocus();
		Timer timer = new Timer();
		TimerTask task = new TimerTask() {
			int moveIndex = 0;
			int speed = 5*level;
			@Override
			public void run() {
				if (STATE) {
					if (moveIndex % speed == 0) {
						moveDownAction();
						moveIndex = 0;
					}
				}
				moveIndex++;
				repaint();
			}
		};
		timer.schedule(task, 10, 20);
}
	public void testAction() {
		for (int i = 0; i < tetromino.cells.length; i++) {
			System.out.println(tetromino.cells[i].getRow()+" "+tetromino.cells[i].getCol());
		}
	}
	public void keyMoveAction(int k) {
		switch (k) {
			case KeyEvent.VK_RIGHT:moveRightAction();break;
			case KeyEvent.VK_LEFT:moveLeftAction();break;
			case KeyEvent.VK_DOWN:moveDownAction();break;
			case KeyEvent.VK_UP:spinCellAction();break;
			case KeyEvent.VK_I:moveInitAction();break;
			case KeyEvent.VK_P:STATE = false;break;
			case KeyEvent.VK_C:STATE = true;break; 
			case KeyEvent.VK_E:System.exit(0);break;
			
		}
	}
	private  void moveInitAction() {//��ʼ��
		STATE = false;
		wall = new Cell[ROWS][COLS];
		tetromino = Tetromino.ranShape();
		nextone = Tetromino.ranShape();
		score = 0;
	}
	private void spinCellAction() {//��ת
		Cell[] nCells=tetromino.spin();
		if(nCells==null) return;
		for(int i=0;i<nCells.length;i++)
		{
			int nRow=nCells[i].getRow();
			int nCol=nCells[i].getCol();
			if(nRow<0||nRow>= ROWS||nCol<0||nCol>=COLS||wall[nRow][nCol]!=null) return;
		}
		tetromino.cells=nCells;

	}
	private void moveDownAction() {
		if (tetromino == null) return;
		if (!Ruler.isBottom()) 
		tetromino.moveDown();
	}
	private void moveLeftAction() {
		if (Ruler.canLeftMove()&&!Ruler.isBottom()) 
		tetromino.moveLeft();
	}
	private void moveRightAction() {
		if (Ruler.canRightMove()&&!Ruler.isBottom()) 
		tetromino.moveRight();
	}
public void paint(Graphics g) {
	   g.drawImage(bg, 0, 0, null);//������
		g.translate(15, 15);//����ϵ��ԭ��
		paintWall(g);
		paintTetromino(g);
		paintNextone(g);
		paintTabs(g);//��ʾ��
		//paintGamePause(g);
		paintGameOver(g);
	}
private void paintGameOver(Graphics g) {
	if (Ruler.isGameOver()) {
		tetromino = null;
		g.drawImage(gameover, -15, -15, null);
		Color color = new Color(0,71,157);
		g.setColor(color);
		Font font = new Font(Font.SERIF,Font.BOLD,30);
		g.setFont(font);
		g.drawString(""+score, 260, 257);
		STATE = false;
	}
}


private void paintTabs(Graphics g) {
	int x = 300;
	int y = 160;
	Color color = new Color(240,234,34);//��ɫ
	g.setColor(color);
	Font f = new Font(Font.SERIF,Font.BOLD,40);//����
	g.setFont(f);
	g.drawString("score:   "+score, x, y);
	y+=56;

}
private void paintNextone(Graphics g) {
	if (nextone == null)return;
	 Cell[] cells = nextone.cells;
	 for(int i = 0; i < cells.length; i++){
		 Cell c = cells[i];
		 int row = c.getRow() ;
		 int col = c.getCol() + 9;
		 int x = col * CELL_SIZE;
		 int y = row * CELL_SIZE;
		 g.drawImage(c.getImage(), x, y, null);
	 }
}
private void paintTetromino(Graphics g) {
	if(tetromino == null)return;
	Cell[] cells = tetromino.cells;
	for(int i=0; i < cells.length; i++){
		Cell c = cells[i];
		int col = c.getCol();
		int row = c.getRow();
		int x = col * CELL_SIZE;
		int y = row * CELL_SIZE;
		g.drawImage(c.getImage(), x, y, null);
	}
}
private void paintWall(Graphics g) {
	for(int row = 0; row < ROWS; row++) {
		for(int col = 0; col < COLS; col++) {
			Cell cell = wall[row][col];
			int rows = row * CELL_SIZE;
			int cols = col * CELL_SIZE;
			if (cell == null) {
			}else{
				g.drawImage(cell.getImage(), cols, rows, null);
			}
		}
	}
}

public static void loginTetris() {//����������
	JFrame frame=new JFrame();
	frame.setSize(500, 400);
	frame.setLocationRelativeTo(null);
	frame.setTitle("Tetris");
	frame.setResizable(false);
	frame.setLayout(null);
	message=new JLabel("�˺�:δ��¼");
	message.setBounds(5,5,400,30);
	frame.add(message);
	
	JButton btn1=new JButton("��ʼ��Ϸ");
	btn1.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			if(id==0) {
				if(JOptionPane.showConfirmDialog(frame, "�Ƿ�򿪵�¼���棿", "δ��¼",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION) {
					frame.setVisible(false);
					 new Account(frame);
				}
			}else {
				frame.setVisible(false);
				startTetris();
			
				}
			}
		
	});
	btn1.setBounds(frame.getWidth()/2-75,130,150,30);
	frame.add(btn1);
	JButton btn2=new JButton("�˺Ź���");
	btn2.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			frame.setVisible(false);
			new Account(frame);
		}
	});
	btn2.setBounds(frame.getWidth()/2-75,170,150,30);
	frame.add(btn2);
	
	frame.setVisible(true);	
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
}


public static void login(int i) {
		id=i;
		try {
			account=new Users().getAccount(i);
		} catch (ClassNotFoundException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
		message.setText("�˺�:"+account);
	}
}

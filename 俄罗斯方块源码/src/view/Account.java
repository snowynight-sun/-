package view;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import model.Users;
import model.Users;
public class Account extends JFrame {
	Users users=new Users();	
	protected JTabbedPane jtab;
	public Account(JFrame mainFrame) {
		this.setSize(500,350);
		this.setLocationRelativeTo(null);
		this.setTitle("账号管理");
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosed(WindowEvent e){
				mainFrame.setVisible(true);
			}
		});
		jtab=new JTabbedPane(JTabbedPane.TOP);
		JPanel panelLogin=new PanelLogin();
		JPanel panelRegister=new PanelRegister();
		jtab.add("登录", panelLogin);
	    jtab.add("注册", panelRegister);
        this.add(jtab);
		this.setVisible(true);
	}
	//登录界面
class PanelLogin extends JPanel{
		PanelLogin(){
			this.setLayout(null);
			JLabel labelAccount=new JLabel("账号:");
			labelAccount.setBounds(80,40,50,30);
			this.add(labelAccount);
			JTextField textAccount=new JTextField();
			textAccount.setBounds(130,40,250,30);
			this.add(textAccount);
			JLabel labelPass=new JLabel("密码:");
			labelPass.setBounds(80,80,50,30);
			this.add(labelPass);
			JPasswordField textPass=new JPasswordField();
			textPass.setBounds(130,80,250,30);
			this.add(textPass);
			JButton btnLogin=new JButton("登录");
			btnLogin.setBounds(180, 130, 100, 30);
			btnLogin.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					if(textAccount.getText().isEmpty() || String.valueOf(textPass.getPassword()).isEmpty()) {
						JOptionPane.showMessageDialog(textAccount, "账号和密码不能为空", "登录失败", JOptionPane.WARNING_MESSAGE);
					}
					else {
						int id=0;
						try {
							id = Users.login(textAccount.getText(),String.valueOf(textPass.getPassword()));
						} catch (ClassNotFoundException e) {
							// TODO 自动生成的 catch 块
							e.printStackTrace();
						} catch (SQLException e) {
							// TODO 自动生成的 catch 块
							e.printStackTrace();
						}
						
						if(id!=0) {
							Tetris.login(id);
							Account.this.dispose();//
							}
						else {
							JOptionPane.showMessageDialog(textAccount, "账号或密码错误", "登录失败", JOptionPane.ERROR_MESSAGE);
						}
					}
				}
			});
			this.add(btnLogin);
		}
		}	
		//注册界面
	class PanelRegister extends JPanel{
			PanelRegister(){
				this.setLayout(null);
				JLabel labelAccount=new JLabel("账号:");
				labelAccount.setBounds(80,40,50,30);
				this.add(labelAccount);
				JTextField textAccount=new JTextField();
				textAccount.setBounds(130,40,250,30);
				this.add(textAccount);
				JLabel labelPass=new JLabel("密码:");
				labelPass.setBounds(80,80,50,30);
				this.add(labelPass);
				JPasswordField textPass=new JPasswordField();
				textPass.setBounds(130,80,250,30);
				this.add(textPass);
				JButton btnRegister=new JButton("注册");
				btnRegister.setBounds(180, 130, 100, 30);
				btnRegister.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent arg0) {
						if(textAccount.getText().isEmpty() || String.valueOf(textPass.getPassword()).isEmpty()) {
							JOptionPane.showMessageDialog(textAccount, "账号、密码不能为空", "注册失败", JOptionPane.WARNING_MESSAGE);
						}
						else {
							try {
								if(Users.isExist(textAccount.getText())){
									JOptionPane.showMessageDialog(textAccount, "账号已存在", "注册失败", JOptionPane.ERROR_MESSAGE);
								}
								else {
									try {
										try {
											Users.addUser(Users.getUserCount()+1,textAccount.getText() ,String.valueOf(textPass.getPassword()),0,0);
										} catch (ClassNotFoundException e) {
											// TODO 自动生成的 catch 块
											e.printStackTrace();
										}
									} catch (SQLException e) {
										// TODO 自动生成的 catch 块
										e.printStackTrace();
									}
									JOptionPane.showMessageDialog(textAccount, "注册成功", "注册成功", JOptionPane.INFORMATION_MESSAGE);
								}
							} catch (HeadlessException e) {
								// TODO 自动生成的 catch 块
								e.printStackTrace();
							} catch (ClassNotFoundException e) {
								// TODO 自动生成的 catch 块
								e.printStackTrace();
							} catch (SQLException e) {
								// TODO 自动生成的 catch 块
								e.printStackTrace();
							}
						}
					}
				});
				this.add(btnRegister);
			}
					
				
}	
}
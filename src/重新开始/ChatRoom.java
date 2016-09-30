package 重新开始;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class ChatRoom extends JFrame implements ActionListener,Runnable {
	private JComboBox<String> skinBox;
	private JLabel backLabel;
	private JTextField dialogField;
	private JTextArea dialogArea;
	private Login login;
	private String number;
	Thread thread = null;
	DatagramPacket sendPack, receivePack;
	DatagramSocket sendSocket, receiveSocket;
	String outBuf;
	private TestJList list;
	String name ;
	String code ;
	public static Boolean game=true;
	public static Boolean allow=true;
	
	private JDialog jDialog;
	JLabel linkLabel;
	/**
	 * @return the dialogArea
	 */
	public JTextArea getDialogArea() {
		return dialogArea;
	}
	@SuppressWarnings("restriction")
	public ChatRoom(final Login login, TestJList list, String str) {
		String array[] = TestJList.str.split("_");
		Pattern p = Pattern.compile("[\\u4e00-\\u9fa5]+|\\d+");
		Matcher m = p.matcher(array[1]);
		m.find();
		 name = m.group();
		m.find();
		code= m.group();
		
		 linkLabel=new JLabel("正在等待对方连接..........");
			jDialog=new JDialog();
			jDialog.add(linkLabel);
		this.login = login;
		this.list = list;
		this.setSize(554, 424);
		LoginUI.setDragable(this);
		SetCenter.setScreenCenter(this);

		Toolkit kit=Toolkit.getDefaultToolkit();
		Image image=kit.getImage("image\\logo.jpg");
		this.setIconImage(image);
		
		this.getContentPane().setLayout(null);
		/** 添加关闭按钮 */
		this.getContentPane().setLayout(null);
		JButton button3 = new JButton();
		button3.setBounds(505, 8, 25, 25);
		button3.setIcon(LoginUI.getImageIcon("退出.jpg", 25, 25));
		button3.addActionListener(this);
		button3.setActionCommand("exit");

		final String array1[] = str.split("_");
		JLabel piclbl = new JLabel("（头像）");
		piclbl.setIcon(LoginUI.getImageIcon(array1[1], 60, 52));
		piclbl.setBounds(14, 13, 63, 52);
		piclbl.setToolTipText("点击头像查看好友详细信息");
		Pattern p1 = Pattern.compile("[\\u4e00-\\u9fa5]+|\\d+");
		Matcher m1 = p1.matcher(array1[0]);
		m1.find();
		m1.find();
		number = m1.group();
		// JOptionPane.showMessageDialog(this, number);
		piclbl.addMouseListener(new MouseAdapter() {
			@SuppressWarnings("unused")
			public void mouseClicked(MouseEvent e) {

				new Information1(login, number);
			}
		});

		JLabel namlbl = new JLabel(array1[0]);
		namlbl.setBounds(190, 13, 162, 18);

		dialogField = new JTextField();
		dialogField.addActionListener(this);
		dialogField.setBounds(14, 317, 301, 38);
		dialogField.setColumns(10);

		JButton sendbtn = new JButton("发送");
		sendbtn.setBounds(329, 317, 93, 38);
		sendbtn.setFont(new Font("宋体", Font.PLAIN, 20));
		sendbtn.setForeground(Color.blue);
		sendbtn.setBackground(Color.white);
		sendbtn.addActionListener(this);

		
		

		skinBox = new JComboBox<String>();
		skinBox.setBounds(362, 27, 107, 24);
		skinBox.addItem("更换皮肤");
		skinBox.addItem("纯1");
		skinBox.addItem("纯2");
		skinBox.addItem("画1");
		skinBox.addItem("画2");
		skinBox.addItem("画3");
		skinBox.addItem("花");
		skinBox.addItem("城");
		skinBox.addItem("静1");
		skinBox.addItem("静2");
		skinBox.addItem("静3");
		skinBox.addItem("镜海");
		skinBox.setToolTipText("点击更换皮肤");
		skinBox.setActionCommand("skin");

		skinBox.addActionListener(this);
		sendbtn.addActionListener(this);
	

		this.getContentPane().add(skinBox);

		/** 添加背景图片 */
		backLabel = new JLabel();
		backLabel.setSize(this.getSize());
		backLabel.setLocation(0, 0);
		backLabel.setIcon(LoginUI.getImageIcon("纯1.jpg", this.getWidth(), this.getHeight()));
		this.getRootPane().add(backLabel);
		JComponent contentPane = (JComponent) this.getContentPane();

		/** 设置圆角 */
		this.setUndecorated(true);
		com.sun.awt.AWTUtilities.setWindowShape(this,
				new RoundRectangle2D.Double(0.0D, 0.0D, this.getWidth(), this.getHeight(), 90.0D, 90.0D));
		dialogField.setOpaque(false);
		sendbtn.setOpaque(false);
		

		dialogArea = new JTextArea();
		dialogArea.setFont(new Font("宋体", Font.PLAIN, 15));
		dialogArea.setBounds(14, 62, 516, 245);
		dialogArea.setEditable(false);

		dialogArea.setOpaque(false);
		contentPane.setOpaque(false);

		JScrollPane scrollPane = new JScrollPane(dialogArea);
		scrollPane.setBounds(14, 62, 516, 245);
		scrollPane.setOpaque(false);
		scrollPane.getViewport().setOpaque(false);

		this.getContentPane().add(button3);
		this.getContentPane().add(piclbl);
		this.getContentPane().add(namlbl);
		this.getContentPane().add(scrollPane);
		this.getContentPane().add(dialogField);
		this.getContentPane().add(sendbtn);
		

		JButton button = new JButton();
		button.setIcon(LoginUI.getImageIcon("small.jpg", 25, 24));
		button.addActionListener(this);
		button.setActionCommand("small");
		button.setBounds(479, 8, 25, 24);
		getContentPane().add(button);
		this.toFront();
	//	chat();
		this.setVisible(true);

	}

	/**
	 * @return the number
	 */
	public String getNumber() {
		return number;
	}
	/**
	 * @param number the number to set
	 */
	public void setNumber(String number) {
		this.number = number;
	}
	

	//判断是否在线
	public String getPort() {
		try {
			login.getOut().writeUTF("chat_" + number);
			String array[] = login.getIn().readUTF().split("_");
			if (array[0].equals("0")) {
				JOptionPane.showMessageDialog(this, "该用户不在线");
				this.dispose();
				return null;
			}

			return array[1];
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		return null;

	}

	
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
	
	}

	
	
	
	@SuppressWarnings("static-access")
	@Override
	public void actionPerformed(ActionEvent e) {
		if ("游戏".equals(e.getActionCommand())) {
			if(game){
				String localname=null;
				InetAddress addr=null;
				try {
					InetAddress	 ia=addr.getLocalHost();
					 localname=ia.getHostAddress();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				game=false;
				allow=true;
				try {
					list.getOut().writeUTF(number+"_"+code +"_game_"+localname);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					Thread.sleep(500);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				if(allow){
				Thread t=new Thread(this);
				t.start();
				}
			}
			else{
				JOptionPane.showMessageDialog(this, "您已经在进行游戏了！");
			}
		} else if ("small".equals(e.getActionCommand())) {
			this.setExtendedState(JFrame.ICONIFIED);
		} else if ("exit".equals(e.getActionCommand())) {
		    list.getRooms().remove(this);
		   
			this.dispose();
		} else if ("skin".equals(e.getActionCommand())) {
			backLabel.setIcon(LoginUI.getImageIcon((skinBox.getSelectedItem().toString()) + ".jpg", 554, 424));

			// 点击发送按钮或者在文本框中回车
		} else {
			if (!(dialogField.getText().equals(""))) {
				String a=getPort();
				if(a==null) {
					if(list.getRooms().size()!=0){
						list.getRooms().remove(this);
					return;
					}
					return;
				}
				outBuf = number+"_"+code +"_"+name + "[" + code + "]说 ： " + dialogField.getText();
				try {
					list.getOut().writeUTF(outBuf);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				dialogArea.append("我说:" + dialogField.getText() + "\n");
				dialogArea.setCaretPosition(dialogArea.getText().length());
				dialogField.setText(null);
			} else {
				JOptionPane.showMessageDialog(this, "发送消息不能为空！");
			}
		}
	}

	
}

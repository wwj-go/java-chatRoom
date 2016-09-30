package 重新开始;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.TextArea;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class MyInfo extends JFrame implements ActionListener {
	private JComponent pan1;
	private JComponent pan2;
	private JComponent iconPane;
	private JLabel backLabel;
	private JLabel piclbl;
	private JLabel numlbla;
	private JLabel numlbl2;
	private JLabel birlbl2;
	private JTextField namlbla;
	private JTextField namlbl2;
	private JTextField sexlbl2;
	private JTextField poslbl2;
	private TextArea desArea;
	private JButton OKbtn;
	boolean flag = false;
	private Login login;
	private String picture=null;
	private JButton button;
	@SuppressWarnings("restriction")
	public MyInfo(Login login) {
		this.login = login;

		this.setSize(600, 405);
		LoginUI.setDragable(this);
		SetCenter.setScreenCenter(this);

		/** 设置圆角 */
		this.setUndecorated(true);
		com.sun.awt.AWTUtilities.setWindowShape(this,
				new RoundRectangle2D.Double(0.0D, 0.0D, this.getWidth(), this.getHeight(), 90.0D, 90.0D));

		/** 添加背景图片 */

		backLabel = new JLabel(LoginUI.getImageIcon("1115.jpg", this.getWidth(), this.getHeight()));
		backLabel.setSize(this.getSize());
		backLabel.setLocation(0, 0);

		this.getRootPane().add(backLabel);
		this.getContentPane().add(getPanel1());

		piclbl = new JLabel("（头像）");
		piclbl.setBounds(14, 13, 100, 80);
		pan1.add(piclbl);
		piclbl.setBorder(BorderFactory.createLineBorder(Color.blue, 3, true));
		
		button = new JButton();
		button.setIcon(LoginUI.getImageIcon("small.jpg", 25, 25));
		button.addActionListener(this);
		button.setActionCommand("small");
		button.setBounds(531, 13, 25, 25);
		pan1.add(button);
		this.getContentPane().add(getPanel2());

		OKbtn = new JButton("提交");
		OKbtn.setBounds(246, 266, 85, 27);
		pan2.add(OKbtn);
		OKbtn.setFont(new Font("宋体", Font.PLAIN + Font.BOLD, 20));
		OKbtn.setForeground(Color.BLUE);
		OKbtn.setBackground(Color.white);
		OKbtn.setBorder(BorderFactory.createLineBorder(Color.blue));
		OKbtn.addActionListener(this);
		OKbtn.setVisible(flag);
		JComponent contentPane = (JComponent) this.getContentPane();
		contentPane.setOpaque(false);
		
		Toolkit kit=Toolkit.getDefaultToolkit();
		Image image=kit.getImage("image\\logo.jpg");
		this.setIconImage(image);
		
		this.getRootPane().setVisible(true);
		this.setVisible(true);
		addData();
	}

	
	//从服务器端接受信息
		private void addData(){
			try{
				login.getOut().writeUTF("myInformation");
				String str = login.getIn().readUTF();
				String array[] = str.split("_");
				picture=array[0];
				piclbl.setIcon(LoginUI.getImageIcon(array[0], 100, 80));
				numlbl2.setText(array[1]);
				numlbla.setText(array[1]);
				namlbl2.setText(array[2]);
				namlbla.setText(array[2]);
				sexlbl2.setText(array[3]);
				birlbl2.setText(array[4]);
				poslbl2.setText(array[5]);
				desArea.setText(array[6]);			
			}catch(IOException ioe){
				ioe.printStackTrace();
			}
		}


	public JComponent getPanel1() {
		pan1 = new JPanel();
		pan1.setBounds(0, 0, 600, 106);
		pan1.setLayout(null);
		numlbla = new JLabel("（账号）");
		numlbla.setBounds(326, 23, 118, 31);
		namlbla = new JTextField("（昵称）");
		namlbla.setBounds(154, 23, 118, 25);
		namlbla.setEditable(flag);
		namlbla.setOpaque(false);
		JButton addbtn = new JButton("编辑资料");
		addbtn.setBounds(154, 61, 113, 27);
		addbtn.setFont(new Font("宋体", Font.PLAIN + Font.BOLD, 20));
		addbtn.setForeground(Color.BLUE);
		addbtn.setBackground(Color.white);
		addbtn.setBorder(BorderFactory.createLineBorder(Color.blue));
		addbtn.addActionListener(this);

		/** 添加关闭按钮 */
		this.getContentPane().setLayout(null);

		JButton button3 = new JButton();
		button3.setBounds(561, 13, 25, 25);
		button3.setIcon(LoginUI.getImageIcon("退出.jpg", 25, 25));
		button3.addActionListener(this);
		button3.setActionCommand("exit");

		pan1.add(button3);
		pan1.add(numlbla);
		pan1.add(namlbla);
		pan1.add(addbtn);
		addbtn.setOpaque(false);
		pan1.setOpaque(false);

		return pan1;
	}

	public JComponent getPanel2() {
		pan2 = new JPanel();
		pan2.setBounds(0, 107, 600, 293);
		pan2.setLayout(null);

		JLabel numlbl1 = new JLabel("账号");
		numlbl1.setBounds(14, 13, 42, 18);
		numlbl1.setFont(new Font("宋体", Font.LAYOUT_LEFT_TO_RIGHT, 17));
		numlbl2 = new JLabel("（账号）");
		numlbl2.setBounds(85, 14, 104, 18);
		JLabel namlbl1 = new JLabel("昵称");
		namlbl1.setBounds(259, 13, 42, 18);
		namlbl1.setFont(new Font("宋体", Font.LAYOUT_LEFT_TO_RIGHT, 17));
		namlbl2 = new JTextField("（昵称）");
		namlbl2.setBounds(370, 14, 184, 18);
		namlbl2.setEditable(flag);
		namlbl2.setOpaque(false);
		JLabel sexlbl1 = new JLabel("性别");
		sexlbl1.setBounds(14, 58, 42, 18);
		sexlbl1.setFont(new Font("宋体", Font.LAYOUT_LEFT_TO_RIGHT, 17));
		sexlbl2 = new JTextField("（性别）");
		sexlbl2.setBounds(85, 59, 104, 18);
		sexlbl2.setEditable(flag);
		sexlbl2.setOpaque(false);
		JLabel birlbl1 = new JLabel("生日");
		birlbl1.setBounds(259, 58, 42, 18);
		birlbl1.setFont(new Font("宋体", Font.LAYOUT_LEFT_TO_RIGHT, 17));
		birlbl2 = new JLabel("（生日）");
		birlbl2.setBounds(370, 59, 184, 18);
		JLabel poslbl1 = new JLabel("所在地");
		poslbl1.setBounds(14, 99, 72, 18);
		poslbl1.setFont(new Font("宋体", Font.LAYOUT_LEFT_TO_RIGHT, 17));
		poslbl2 = new JTextField("（所在地）");
		poslbl2.setBounds(85, 100, 104, 18);
		poslbl2.setEditable(flag);
		poslbl2.setOpaque(false);
		JLabel deslbl = new JLabel("个人介绍");
		deslbl.setBounds(259, 99, 72, 18);
		deslbl.setFont(new Font("宋体", Font.LAYOUT_LEFT_TO_RIGHT, 17));
		desArea = new TextArea("", 50, 40, TextArea.SCROLLBARS_VERTICAL_ONLY);
		desArea.setEditable(false);
		desArea.setText("（个人介绍）");
		desArea.setBounds(349, 98, 237, 66);
		desArea.setEditable(flag);

		pan2.add(numlbl1);
		pan2.add(numlbl2);
		pan2.add(namlbl1);
		pan2.add(namlbl2);
		pan2.add(sexlbl1);
		pan2.add(sexlbl2);
		pan2.add(birlbl1);
		pan2.add(birlbl2);
		pan2.add(poslbl1);
		pan2.add(poslbl2);
		pan2.add(deslbl);
		pan2.add(desArea);
		pan2.setOpaque(false);

		return pan2;

	}

	// 提供图片
	private void makeIcon() {
		iconPane = new JPanel();
		GridLayout layout = new GridLayout(1, 0);
		layout.setHgap(20);
		iconPane.setLayout(layout);
		String pic[] = {"100","101", "102", "103", "104", "105", "106", "107", "108", "109", "110", "111", "112", "113",
				"114", "115", "116", "117", "118", "119", "120", "121", "122", "123", "124", "125" };

		for (int i = 0; i <= 25; i++) {
			final String s = pic[i] + ".jpg";
			JLabel l = new JLabel();
			l.setIcon(LoginUI.getImageIcon(s, 50, 50));
			l.addMouseListener(new MouseAdapter() {
				public void mousePressed(MouseEvent e) {
					piclbl.setIcon(LoginUI.getImageIcon(s, 100, 80));
					picture=s;
				}
			});
			iconPane.add(l);
		}
	}

	// 编辑资料
	public void edit() {
		flag = true;
		namlbla.setOpaque(false);
		namlbl2.setEditable(flag);
		sexlbl2.setEditable(flag);
		poslbl2.setEditable(flag);
		desArea.setEditable(flag);
		OKbtn.setVisible(flag);

		makeIcon();
		JScrollPane jS = new JScrollPane();
		jS.setViewportView(iconPane);
		jS.setSize(580, 80);
		jS.setLocation(10, 283);
		getContentPane().add(jS);
	}

	private void check(){
		if (namlbl2.getText().equals("")) {
			JOptionPane.showMessageDialog(this, "昵称不能为空！\n 请输入昵称", "警告信息", JOptionPane.WARNING_MESSAGE);
			return;
		}
		if ("".equals(sexlbl2.getText())) {
			JOptionPane.showMessageDialog(this, "性别不能为空！，\n  请重新输入", "错误信息", JOptionPane.ERROR_MESSAGE);
			return;
		}
		if ("".equals(poslbl2.getText())) {
			JOptionPane.showMessageDialog(this, "所在地不能为空！，\n  请重新输入", "错误信息", JOptionPane.ERROR_MESSAGE);
			return;
		}
		if ("".equals(desArea.getText())) {
			JOptionPane.showMessageDialog(this, "自我介绍不能为空！，\n  请重新输入", "错误信息", JOptionPane.ERROR_MESSAGE);
			return;
		}
		// 判断是否选择头像
		if (picture == null) {
			JOptionPane.showMessageDialog(this, "请选择头像", "警告信息", JOptionPane.WARNING_MESSAGE);
			return;
		}
		try {
			login.getOut().writeUTF("modify_" + picture + "_" + namlbl2.getText() + "_" + sexlbl2.getText()
			+ "_" + poslbl2.getText()+ "_" + desArea.getText());
	String bool=	login.getIn().readUTF();
	if("true".equals(bool)){
		TestJList.AddData(TestJList.getM());
		JOptionPane.showMessageDialog(this, "修改信息成功！");
		this.dispose();
	}else{
		JOptionPane.showMessageDialog(this, "修改信息失败！");
	}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if ("small".equals(e.getActionCommand())) {
			this.setExtendedState(JFrame.ICONIFIED);
		}else if ("exit".equals(e.getActionCommand())) {
				this.dispose();
		} else if ("编辑资料".equals(e.getActionCommand())) {
			edit();
		} else if ("提交".equals((e.getActionCommand()))) {
			JOptionPane.showMessageDialog(this, "确认提交？", "提示信息", JOptionPane.OK_CANCEL_OPTION);
			check();
			this.dispose();
		}

	}
}

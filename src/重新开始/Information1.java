package 重新开始;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class Information1 extends JFrame implements ActionListener {
	private JComponent pan1;
	private JComponent pan2;
	private JLabel backLabel;
	private Login login;
	private String number;
	JLabel numlbla;
	JLabel namlbla;
	JLabel piclbl;
	JLabel numlbl2;
	JLabel namlbl2;
	JLabel sexlbl2;
	JLabel birlbl2;
	JLabel poslbl2;
	JTextArea desArea;

	private void AddData() {
		try {
			login.getOut().writeUTF("find_" + number);
			String str = login.getIn().readUTF();
			if(str.equals("false")){
				this.setVisible(false);
				JOptionPane.showMessageDialog(this, "该用户不存在");
				this.dispose();
				return;
			}
			String array[] = str.split("_");
			piclbl.setIcon(LoginUI.getImageIcon(array[0], 126, 126));
			numlbla.setText(number);
			namlbla.setText(array[1]);
			numlbl2.setText(number);
			namlbl2.setText(array[1]);
			sexlbl2.setText(array[2]);
			birlbl2.setText(array[3]);
			poslbl2.setText(array[4]);
			desArea.setText(array[5]);

		} catch (IOException e) {
			e.printStackTrace();
			this.setVisible(false);
			JOptionPane.showMessageDialog(this, "该用户不存在");
			this.dispose();
			return;
			
			
		}
	}

	@SuppressWarnings("restriction")
	public Information1(Login login, String number) {
		
		Toolkit kit=Toolkit.getDefaultToolkit();
		Image image=kit.getImage("image\\logo.jpg");
		this.setIconImage(image);
		
		this.login = login;
		this.number = number;
		this.setSize(480, 480);
		LoginUI.setDragable(this);
		SetCenter.setScreenCenter(this);

		/** 设置圆角 */
		this.setUndecorated(true);
		com.sun.awt.AWTUtilities.setWindowShape(this,
				new RoundRectangle2D.Double(0.0D, 0.0D, this.getWidth(), this.getHeight(), 90.0D, 90.0D));

		/** 添加背景图片 */

		backLabel = new JLabel(LoginUI.getImageIcon("2226.jpg", this.getWidth(), this.getHeight()));
		backLabel.setSize(this.getSize());
		backLabel.setLocation(0, 0);
		
	JButton	button = new JButton();
		button.addActionListener(this);
		button.setActionCommand("small");
		button.setIcon(LoginUI.getImageIcon("small.jpg", 25, 25));
		button.setBounds(401, 5, 25, 25);
		
		
		
		this.getRootPane().add(backLabel);
		this.getContentPane().add(getPanel1());
		this.getContentPane().add(getPanel2());
		JComponent contentPane = (JComponent) this.getContentPane();
		contentPane.setOpaque(false);
		pan1.add(button);
		this.getRootPane().setVisible(true);
		this.setVisible(true);
		AddData();
	}

	public JComponent getPanel1() {
		pan1 = new JPanel();
		pan1.setBounds(0, 0, 471, 152);
		pan1.setLayout(null);

		piclbl = new JLabel("（头像）");
		piclbl.setBounds(14, 13, 126, 126);
		piclbl.setBorder(BorderFactory.createLineBorder(Color.blue, 3, true));
		numlbla = new JLabel("（账号）");
		numlbla.setBounds(294, 36, 118, 31);
		namlbla = new JLabel("（昵称）");
		namlbla.setBounds(154, 36, 126, 31);
	
		/** 添加关闭按钮 */
		this.getContentPane().setLayout(null);

		JButton button3 = new JButton();
		button3.setBounds(430, 5, 25, 25);
		button3.setIcon(LoginUI.getImageIcon("退出.jpg", 25, 25));
		button3.addActionListener(this);
		button3.setActionCommand("exit");

		pan1.add(button3);
		pan1.add(piclbl);
		pan1.add(numlbla);
		pan1.add(namlbla);
	
		pan1.setOpaque(false);

		return pan1;
	}

	public JComponent getPanel2() {
		pan2 = new JPanel();
		pan2.setBounds(0, 153, 471, 401);
		pan2.setLayout(null);

		JLabel numlbl1 = new JLabel("账号");
		numlbl1.setBounds(14, 13, 72, 18);
		numlbl1.setFont(new Font("宋体", Font.LAYOUT_LEFT_TO_RIGHT, 17));
		numlbl2 = new JLabel("（账号）");
		numlbl2.setBounds(121, 13, 184, 18);
		JLabel namlbl1 = new JLabel("昵称");
		namlbl1.setBounds(14, 55, 72, 18);
		namlbl1.setFont(new Font("宋体", Font.LAYOUT_LEFT_TO_RIGHT, 17));
		namlbl2 = new JLabel("（昵称）");
		namlbl2.setBounds(121, 55, 184, 18);
		JLabel sexlbl1 = new JLabel("性别");
		sexlbl1.setBounds(14, 102, 72, 18);
		sexlbl1.setFont(new Font("宋体", Font.LAYOUT_LEFT_TO_RIGHT, 17));
		sexlbl2 = new JLabel("（性别）");
		sexlbl2.setBounds(121, 102, 184, 18);
		JLabel birlbl1 = new JLabel("生日");
		birlbl1.setBounds(14, 151, 72, 18);
		birlbl1.setFont(new Font("宋体", Font.LAYOUT_LEFT_TO_RIGHT, 17));
		birlbl2 = new JLabel("（生日）");
		birlbl2.setBounds(121, 151, 184, 18);
		JLabel poslbl1 = new JLabel("所在地");
		poslbl1.setBounds(14, 198, 72, 18);
		poslbl1.setFont(new Font("宋体", Font.LAYOUT_LEFT_TO_RIGHT, 17));
		poslbl2 = new JLabel("（所在地）");
		poslbl2.setBounds(121, 198, 184, 18);
		JLabel deslbl = new JLabel("个人介绍");
		deslbl.setBounds(14, 244, 72, 18);
		deslbl.setFont(new Font("宋体", Font.LAYOUT_LEFT_TO_RIGHT, 17));
		desArea = new JTextArea();
		desArea.setEditable(false);
		desArea.setText("（个人介绍）");
		desArea.setBounds(121, 242, 322, 146);

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

		desArea.setOpaque(false);
		pan2.setOpaque(false);

		return pan2;

	}

	public static void main(String[] args) {
		new Information(new Login(), null);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if ("exit".equals(e.getActionCommand())) {
			this.dispose();
		}
		else if ("small".equals(e.getActionCommand())) {
			this.setExtendedState(JFrame.ICONIFIED);
		}
	}
}

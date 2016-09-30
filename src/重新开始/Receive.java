/** 
* @author  作者：王伟军
 E-mail: 
* @date 创建时间：2016年3月4日 下午12:05:43 
* @version  
* @parameter  
* @since  
* @return  
*/
package 重新开始;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.RoundRectangle2D;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

@SuppressWarnings("serial")
public class Receive extends JFrame implements MouseListener{
	private	JLabel code;
private 	JButton OkButton;
private JLabel iconLabel;
private JLabel clickLabel ;
private String number;
private Login login;
private JComponent contentPane;
private JComponent rootPane;
private JLabel backLabel;
	@SuppressWarnings("restriction")
	public Receive(JFrame login,String str,String info){
		this.setSize(600, 400);
	this.number=str;
	this.login =(Login) login;
		this.getContentPane().setLayout(null);
		
		backLabel=new JLabel(LoginUI.getImageIcon("静2.jpg", 600, 400));
		backLabel.setSize(this.getSize());
		backLabel.setLocation(0, 0);
		rootPane=this.getRootPane();
		rootPane.add(backLabel);
		rootPane.setVisible(true);
		 OkButton = new JButton("\u786E\u5B9A");
		 OkButton.setBackground(Color.WHITE);
		 OkButton.setFont(new Font("宋体", Font.PLAIN, 20));
		OkButton.setBounds(281, 345, 90, 28);
		OkButton.addMouseListener(this);
		getContentPane().add(OkButton);
		
		JLabel lblNewLabel = new JLabel("\u964C\u751F\u4EBA\u6D88\u606F");
		lblNewLabel.setFont(new Font("华文行楷", Font.PLAIN, 30));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(222, 10, 180, 34);
		getContentPane().add(lblNewLabel);
		
		 iconLabel = new JLabel("icon");
		 iconLabel.setHorizontalAlignment(SwingConstants.CENTER);
		iconLabel.addMouseListener(this);
		iconLabel.setIcon(LoginUI.getImageIcon("陌生人.jpg", 240, 201));
		iconLabel.setToolTipText("\u70B9\u51FB\u8FD9\u91CC\u67E5\u770BTa\u7684\u4FE1\u606F");
		iconLabel.setFont(new Font("SimSun", Font.PLAIN, 12));
		iconLabel.setBounds(318, 54, 240, 201);
		getContentPane().add(iconLabel);
		
		 clickLabel = new JLabel("\u8FD9\u91CC");
		 clickLabel.setToolTipText("\u70B9\u51FB\u8FD9\u91CC\u67E5\u770B\u8BE6\u7EC6\u4FE1\u606F");
		clickLabel.setForeground(Color.RED);
		clickLabel.addMouseListener(this);
		clickLabel.setFont(new Font("楷体", Font.PLAIN, 20));
		clickLabel.setHorizontalAlignment(SwingConstants.CENTER);
		clickLabel.setBounds(382, 287, 40, 34);
		getContentPane().add(clickLabel);
		
		JLabel label = new JLabel("\u70B9\u51FB");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setForeground(Color.BLACK);
		label.setFont(new Font("楷体", Font.PLAIN, 15));
		label.setBounds(343, 289, 40, 34);
		getContentPane().add(label);
		
		JLabel lblNewLabel_3 = new JLabel("\u67E5\u770Bta\u7684\u8BE6\u7EC6\u4FE1\u606F");
		lblNewLabel_3.setFont(new Font("楷体", Font.PLAIN, 12));
		lblNewLabel_3.setBounds(432, 299, 103, 15);
		getContentPane().add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("\u8D26\u53F7\uFF1A");
		lblNewLabel_4.setFont(new Font("楷体", Font.BOLD, 20));
		lblNewLabel_4.setBounds(32, 69, 78, 34);
		getContentPane().add(lblNewLabel_4);
		
		 code = new JLabel(number);
		code.setToolTipText("\u70B9\u51FB\u8FD9\u91CC\u67E5\u770B\u8BE6\u7EC6\u4FE1\u606F");
		code.setForeground(Color.BLUE);
		code.addMouseListener(this);
		code.setFont(new Font("宋体", Font.PLAIN, 20));
		code.setHorizontalAlignment(SwingConstants.CENTER);
		code.setBounds(92, 69, 202, 34);
		getContentPane().add(code);
		
		JLabel label_1 = new JLabel("\u6D88\u606F\u5185\u5BB9\uFF1A");
		label_1.setFont(new Font("楷体", Font.BOLD, 20));
		label_1.setBounds(10, 133, 116, 39);
		getContentPane().add(label_1);
		
		
		JTextArea textArea = new JTextArea();
		textArea.append(info);
		textArea.setFont(new Font("楷体", Font.PLAIN, 18));
		textArea.setBounds(20, 178, 253, 158);
		textArea.setEditable(false);
		textArea.setOpaque(false);
		
		JScrollPane jp = new JScrollPane(textArea);
		jp.setBounds(20, 178, 253, 158);
		jp.setOpaque(false);
		jp.getViewport().setOpaque(false);
		
		getContentPane().add(jp);
		this.setUndecorated(true);
		com.sun.awt.AWTUtilities.setWindowShape(this,
				new RoundRectangle2D.Double(0.0D, 0.0D, this.getWidth(), this.getHeight(), 100.0D, 90.0D));
		LoginUI.setDragable(this);
		Toolkit kit=Toolkit.getDefaultToolkit();
		Image image=kit.getImage("image\\logo.jpg");
		this.setIconImage(image);
		
		contentPane=(JComponent) this.getContentPane();
		contentPane.setOpaque(false);
		
		
		this.setDefaultCloseOperation(3);
	    SetCenter.setScreenCenter(this);
		this.setVisible(true);
		
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getSource()==OkButton){
			this.dispose();
		}
		else{
			new Information(login,number);
			this.dispose();
		}
			
		}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
	 */
	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}

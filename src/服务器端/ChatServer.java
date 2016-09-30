/** 
* @author  作者：王伟军
 E-mail: 
* @date 创建时间：2016年3月4日 下午12:05:43 
* @version  
* @parameter  
* @since  
* @return  
*/
package 服务器端;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

//服务器的类
public class ChatServer {
	JFrame frame;
	JPanel pane;
	JButton button1;
	JButton button2;
	boolean started = false;
	ServerSocket ss = null;

	List<Client> clients = new ArrayList<Client>(); // 保存客户端线程类

	// 开启服务器

	public static void main(String[] args) {

		new ChatServer();
	}

	// 服务器的构造方法
	public ChatServer() {
		// 构建运行线程
		final Thread t = new Thread(new CarryOut());
		// 构建图形用户界面
		frame = new JFrame("聊天服务器");
		pane = new JPanel();
		button1 = new JButton("开始服务");
		button2 = new JButton("停止服务");
		// 添加监听
		button1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// 启动服务器的工作线程
				t.start();
				// 设置可点击性
				button2.setEnabled(true);
				button1.setEnabled(false);
			}

		});
		// 添加监听
		button2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				System.exit(1);
			}

		});
		button2.setEnabled(false);
		pane.add(button1);
		pane.add(button2);
		frame.add(pane);
		frame.setLocation(400, 200);
		frame.setSize(400, 100);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	// 工作线程
	// 内部类
	class CarryOut implements Runnable {
		public void run() {
			start1();
		}
	}

	// 开始所有的工作方法
	void start1() {
		try {
			ss = new ServerSocket(6666); // 建立服务端对象
			started = true;
		} catch (BindException e) {
			System.out.println("端口使用中");
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 循环监听客户端的请求，
		// 并为每个请求开启相应的处理线程
		try {
			while (started) {
				Socket s = ss.accept(); // 接收客户端
				Client c = new Client(s);
				System.out.println("接收成功");
				new Thread(c).start(); // 启动线程
				clients.add(c); // 添加线程类

			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				ss.close();
				System.exit(1);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	class Client implements Runnable { // 建立客户端线程接收，发送数据
		// 定义线程类的私有属性
		private Socket s;
		private DataInputStream dis = null;
		private DataOutputStream dos = null;
		private boolean bConnected = false;
		private java.sql.Statement sta;
		private Connection con;
		private ResultSet rs = null;
		private String account = "888888888";
		private int port;

		public int getPort() {
			return port;
		}

		public String getAccout() {
			return account;
		}

		public DataOutputStream getDos() {
			return dos;
		}
		public DataInputStream getDis() {
			return dis;
		}
		public Socket getS(){
			return s;
			
		}

		// 线程类的构造方法
		public Client(Socket s) {
			this.s = s;
			try {
				// 创建输入输出流
				dis = new DataInputStream(s.getInputStream());
				dos = new DataOutputStream(s.getOutputStream());
				bConnected = true;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		// 线程的核心方法
		public void run() {

			// 循环监听客户端的字符流并判断
			while (bConnected) {

				// 拆分客户端的字符
				String str = null;
				try {
					str = dis.readUTF();
					//JOptionPane.showMessageDialog(new JFrame(), "后");
				} catch (IOException e) {
					e.printStackTrace();
				}
				try {
				String m[] = str.split("_");
				// 记录每个登录用户的账号
				if (m[0].equals("登录")) {
					account = m[1];
				} else if ("exit".equals(m[0])) {
					//删除离线的客户
					for (int i = clients.size() - 1; i >= 0; i--) {
						Client c = clients.get(i);
						if (m[0].equals(c.getAccout())) {
							System.out.println(clients.size());
							clients.remove(c);
							System.out.println(clients.size());
							bConnected=false;
							c.getDos().close();
							c.getDis().close();
							c.getS().close();	
						}
					}
				} else {
					for (int i = clients.size() - 1; i >= 0; i--) {
						if (m[0].equals(clients.get(i).getAccout())) {
							if(m.length==3)
								clients.get(i).getDos().writeUTF(m[1] + "_" + m[2]);
							else if(m.length==4)
								clients.get(i).getDos().writeUTF(m[1] + "_" + m[2]+"_" + m[3]);

							//	JOptionPane.showMessageDialog(new JFrame(), m[1] + "_" + m[2]);
							} 
						}
					}

				}catch (IOException e) {
					e.printStackTrace();
				}catch( NullPointerException e){
					bConnected=false;
					System.out.println("下线愉快！");
				}
			}
		}
	}
}

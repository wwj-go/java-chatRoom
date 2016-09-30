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
import java.io.EOFException;
import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

//服务器的类
public class Server {
	JFrame frame;
	JPanel pane;
	JButton button1;
	JButton button2;
	boolean started = false;
	ServerSocket ss = null;
	
	List<Client> clients = new ArrayList<Client>(); // 保存客户端线程类

	// 开启服务器

	public static void main(String[] args) {

		new Server();
	}

	// 服务器的构造方法
	public Server() {
		// 构建运行线程
		final Thread t = new Thread(new CarryOut());
		// 构建图形用户界面
		frame = new JFrame("狗狗聊天服务器");
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
			
			ss = new ServerSocket(8888); // 建立服务端对象
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
				System.out.println("客戶端接收成功");
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
		private String account;
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
			try {
				// 循环监听客户端的字符流并判断
				while (bConnected) {

					// 拆分客户端的字符
					String str = dis.readUTF();
					String m[] = str.split("_");

					// 根据拆分的不同情况进行不同的处理
					if (m[0].equals("登录")) {
						try {
							// 处理登陆请求
							search(m);
							account = m[1];
						} catch (SQLException e) {

							e.printStackTrace();
						}
					} else if (m[0].equals("register")) {
						// 处理注册事件
						try {
							register(m);
						} catch (ClassNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						// 处理查询自己信息事件
					} else if (m[0].equals("myInfo")) {
						queryMe(m);
					} else if (m[0].equals("@")) {
						send(m);
					} else if (m[0].equals("friend")) {
						queryFriend(m);
					} else if (m[0].equals("find")) {
						// 处理取款事件
						findFriend(m);
					} else if (m[0].equals("addFriend")) {
						// 处理转账事件
						addFriend(m);
					} else if (m[0].equals("delete")) {
						// 处理修改密码事件
						delete(m);
					} else if (m[0].equals("myInformation")) {
						// 处理修改密码事件
						myInfo(m);
					} else if (m[0].equals("logout")) {
						// 处理修改密码事件
						logout();
					} else if (m[0].equals("chat")) {
						// 处理修改密码事件
						chat(m);
					} else if (m[0].equals("modify")) {
						// 处理修改密码事件
						try {
							modifyMe(m);
						} catch (ClassNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (Exception e) {
							// TODO Auto-generated catch block
							bConnected=false;
							System.out.println("再见！");
							//e.printStackTrace();
						}
					}
				}
			} catch (java.net.SocketException e) {
				e.printStackTrace();
			} catch (EOFException e) {
				System.out.println("客戶端退出了");
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (dis != null)
					if (s != null)
						try {
							dis.close();
							s.close();
							dos.close();

						} catch (IOException e) {
							e.printStackTrace();
						}
			}
		}

		public void send(String array[]) {
			for (int i = clients.size() - 1; i >= 0; i--) {
				Client client = clients.get(i);
				if (client.getAccout().equals(array[1])) {
					try {
						client.getDos().writeUTF("@_" + this.account);
						client.getDos().flush();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}

		// 处理聊天事件
		public void chat(String array[]) throws IOException {
			try {
				Class.forName("com.mysql.jdbc.Driver");
				String tableName1 = "select  * from user_info where NUM=" + array[1];
				con = DriverManager.getConnection("jdbc:mysql://127.0.0.1/database?useUnicode=true&characterEncoding=utf8", "root", "");
				//jdbc:mysql://127.0.0.1:3306/dbname?useUnicode=true&characterEncoding=GBK
				sta = con.createStatement();
				rs = sta.executeQuery(tableName1);
				rs.next();
				String state = rs.getString(9);
				String ip = rs.getString(10) + "_";
				int port = rs.getInt(11);
				dos.writeUTF(state + "_" + ip + port);
				if (Integer.parseInt(state) == 1) {
					port++;
					String sql4 = "update user_info set PORT=" + port + " where NUM=" + array[1];
					PreparedStatement ps4 = con.prepareStatement(sql4);
					ps4.executeUpdate();
				}
				sta.close();
				con.close();
			} catch (ClassNotFoundException e) {
				dos.writeUTF("false");
				e.printStackTrace();
			} catch (SQLException e) {
				dos.writeUTF("false");
				e.printStackTrace();
			}

		}

		// 修改自己信息
		public void modifyMe(String array[]) throws IOException, ClassNotFoundException, SQLException {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1/database?useUnicode=true&characterEncoding=utf8", "root", "");
			String sql = "update USER_INFO set NAME=?,INFO=?,PIC=?,PLACE=?,SEX=? where NUM=" + account;
			PreparedStatement pre = con.prepareCall(sql);
			pre.clearParameters();
			try {
				// 读取客户发来的信息
				String name = array[2];
				String info = array[5];
				String pic = array[1];
				String sex = array[3];
				String place = array[4];
				pre.setString(1, name);
				pre.setString(2, info);
				pre.setString(3, pic);
				pre.setString(5, sex);
				pre.setString(4, place);
				pre.executeUpdate();

				dos.writeUTF("true");
				pre.close();
			} catch (SQLException e) {
				try {
					e.printStackTrace();
					dos.writeUTF("false");
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				e.printStackTrace();
			} catch (IOException e) {
				try {
					e.printStackTrace();
					dos.writeUTF("false");
				} catch (IOException e1) {

					e1.printStackTrace();
				}
				e.printStackTrace();
			}
		}

		// 删除好友事件
		@SuppressWarnings("null")
		public void delete(String array[]) throws IOException {
			try {
				Class.forName("com.mysql.jdbc.Driver");
				String tableName1 = "select  * from friend where QQNUM=" + account;
				con = DriverManager.getConnection("jdbc:mysql://127.0.0.1/database?useUnicode=true&characterEncoding=utf8", "root", "");
				sta = con.createStatement();
				rs = sta.executeQuery(tableName1);
				rs.next();
				StringBuffer buffer = new StringBuffer(81);
				String s1 = rs.getString(2);
				String array1[] = s1.split("&");

				for (int i = 0; i < array1.length; i++) {
					String a[] = array1[i].split("_");
					if (!(a[0].equals(array[1])))
						buffer.append(array1[i]);
				}
				// JOptionPane.showMessageDialog(new
				// JFrame(),buffer.toString());
				String sql1 = "update friend set friend='" + buffer.toString() + "'" + "  where QQNUM=" + account;
				PreparedStatement ps2 = con.prepareStatement(sql1);
				ps2.executeUpdate();
				sta.close();
				con.close();

				dos.writeUTF("true");

			} catch (ClassNotFoundException e) {
				dos.writeUTF("false");
				e.printStackTrace();
			} catch (SQLException e) {
				dos.writeUTF("false");
				e.printStackTrace();
			}

		}

		// 分割字符串和数字
		public String cut(String str) {
			Pattern p = Pattern.compile("[\\u4e00-\\u9fa5]+|\\d+");
			Matcher m = p.matcher(str);
			m.find();
			m.find();
			return m.group();
		}

		// 这个方法处理下线的用户
		public void logout() {
			try {
				Class.forName("com.mysql.jdbc.Driver");
				con = DriverManager.getConnection("jdbc:mysql://127.0.0.1/database?useUnicode=true&characterEncoding=utf8", "root", "");
			} catch (ClassNotFoundException e3) {
				// TODO Auto-generated catch block
				e3.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			PreparedStatement pre;
			try {
				String sql2 = "update user_info set STATUS=0  where NUM=" + account;
				String sql3 = "update user_info set IP='null'   where NUM=" + account;
				String sql4 = "update user_info set PORT=0 where NUM=" + account;

				PreparedStatement ps2 = con.prepareStatement(sql2);
				ps2.executeUpdate();
				PreparedStatement ps3 = con.prepareStatement(sql3);
				ps3.executeUpdate();
				PreparedStatement ps4 = con.prepareStatement(sql4);
				ps4.executeUpdate();
				dos.writeUTF("true");
				con.close();
				dos.close();
				dis.close();
				s.close();
			} catch (SQLException e) {
				try {
					dos.writeUTF("logoutFail");
				} catch (IOException e1) {

					e1.printStackTrace();
				}
				e.printStackTrace();
			} catch (NumberFormatException e) {
				try {
					dos.writeUTF("logoutFail");
				} catch (IOException e1) {
					try {
						dos.writeUTF("logoutFail");
					} catch (IOException e2) {

						e2.printStackTrace();
					}
					e1.printStackTrace();
				}
				e.printStackTrace();
			} catch (IOException e) {

				e.printStackTrace();
			}

		}

		// 处理添加好友事件的方法
		public void addFriend(String array[]) throws IOException {
			if (account.equals(array[1])) {
				dos.writeUTF("重复");
				return;
			}
			try {
				Class.forName("com.mysql.jdbc.Driver");
				String tableName1 = "select  * from user_info where NUM=" + array[1];
				con = DriverManager.getConnection("jdbc:mysql://127.0.0.1/database?useUnicode=true&characterEncoding=utf8", "root", "");
				sta = con.createStatement();
				// 查询好友的详细信息
				rs = sta.executeQuery(tableName1);
				rs.next();
				String pic = "_" + rs.getString(2) + "_";
				String name = rs.getString(3);
				String info = rs.getString(8);
				String in1 = name + array[1] + pic + info;
				// 读出所有的朋友
				String sql2 = "select  * from friend where QQNUM=" + account;
				sta = con.createStatement();
				rs = sta.executeQuery(sql2);
				rs.next();
				String in = null;
				String out = rs.getString(2);

				if (out == null || out.equals("")) {
				//	JOptionPane.showMessageDialog(new JFrame(), in1);
					in = in1;
				} else {
					String array1[] = out.split("&");
					for (int i = 0; i < array1.length; i++) {
						String a[] = array1[i].split("_");
						if (cut(a[0]).equals(array[1])) {
							sta.close();
							con.close();
							dos.writeUTF("重复");
							return;
						}
					}
					in = out + "&" + in1;
				}
				// JOptionPane.showMessageDialog(new JFrame(), in);
				// 更新好友信息的表
				String sql1 = "update friend set friend='" + in + "'" + "  where QQNUM=" + account;
				PreparedStatement ps2 = con.prepareStatement(sql1);
				ps2.executeUpdate();
				sta.close();
				con.close();
				dos.writeUTF("true");
			} catch (ClassNotFoundException e) {
				dos.writeUTF("false");
				e.printStackTrace();
				return;
			} catch (SQLException e) {
				dos.writeUTF("false");
				e.printStackTrace();
				return;
			}

		}

		// 查找自己的详细信息
		public void myInfo(String array[]) throws IOException {
			try {
				Class.forName("com.mysql.jdbc.Driver");
				String tableName1 = "select  * from user_info where NUM=" + account;
				con = DriverManager.getConnection("jdbc:mysql://127.0.0.1/database?useUnicode=true&characterEncoding=utf8", "root", "");
				sta = con.createStatement();
				rs = sta.executeQuery(tableName1);
				rs.next();
				String pic = rs.getString(2) + "_";
				String name = rs.getString(3) + "_";
				// String password=rs.getString(4)+"_";
				String sex = rs.getString(5) + "_";
				String birth = rs.getString(6) + "_";
				String place = rs.getString(7) + "_";
				String info = rs.getString(8);

				dos.writeUTF(pic + account + "_" + name + sex + birth + place + info);
				sta.close();
				con.close();
			} catch (ClassNotFoundException e) {
				dos.writeUTF("false");
				e.printStackTrace();
			} catch (SQLException e) {
				dos.writeUTF("false");
				e.printStackTrace();
			}

		}

		// 查找好友
		public void findFriend(String array[]) throws IOException {
			try {
				Class.forName("com.mysql.jdbc.Driver");
				String tableName1 = "select  * from user_info where NUM=" + array[1];
				con = DriverManager.getConnection("jdbc:mysql://127.0.0.1/database?useUnicode=true&characterEncoding=utf8", "root", "");
				sta = con.createStatement();
				rs = sta.executeQuery(tableName1);
				rs.next();
				String pic = rs.getString(2) + "_";
				String name = rs.getString(3) + "_";
				String sex = rs.getString(5) + "_";
				String birth = rs.getString(6) + "_";
				String place = rs.getString(7) + "_";
				String info = rs.getString(8);

				dos.writeUTF(pic + name + sex + birth + place + info);
				sta.close();
				con.close();
			} catch (ClassNotFoundException e) {
				dos.writeUTF("false");
				e.printStackTrace();
			} catch (SQLException e) {
				dos.writeUTF("false");
				e.printStackTrace();
			}

		}

		// 登录后刷新查找好友信息
		public void queryFriend(String array[]) throws IOException {
			try {
				Class.forName("com.mysql.jdbc.Driver");
				String tableName1 = "select  * from friend where QQNUM=" + account;
				// String tableName1="select *from " + "客户信息 where
				// 账号=2219962313";
				con = DriverManager.getConnection("jdbc:mysql://127.0.0.1/database?useUnicode=true&characterEncoding=utf8", "root", "");
				sta = con.createStatement();
				rs = sta.executeQuery(tableName1);
				rs.next();
				String s1 = rs.getString(2);
				if (!(s1 == null)) {
					String str = "我" + account + "_0.jpg_我自己的账户&" + s1;
					sta.close();
					con.close();
					dos.writeUTF(str);
				} else
					dos.writeUTF("我" + account + "_0.jpg_我自己的账户");
			} catch (ClassNotFoundException e) {
				dos.writeUTF("false");
				e.printStackTrace();
			} catch (SQLException e) {
				dos.writeUTF("false");
				e.printStackTrace();
			}

		}
//查询自己简单信息的方法
		public void queryMe(String array[]) throws IOException {
			try {
				Class.forName("com.mysql.jdbc.Driver");
				String tableName1 = "select  * from user_info where NUM=" + account;
				// String tableName1="select *from " + "客户信息 where
				// 账号=2219962313";
				con = DriverManager.getConnection("jdbc:mysql://127.0.0.1/database?useUnicode=true&characterEncoding=utf8", "root", "");
				sta = con.createStatement();
				rs = sta.executeQuery(tableName1);
				rs.next();
				String s1 = rs.getString(2);
				String s2 = rs.getString(3);
				sta.close();
				con.close();
				dos.writeUTF(s1 + "_" + s2 + " @@@    " + account);
			} catch (ClassNotFoundException e) {
				dos.writeUTF("false");
				e.printStackTrace();
			} catch (SQLException e) {
				dos.writeUTF("false");
				e.printStackTrace();
			}

		}

		// 处理注册事件的方法
		@SuppressWarnings("unused")
		public void register(String array[]) throws IOException, ClassNotFoundException, SQLException {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1/database?useUnicode=true&characterEncoding=utf8", "root", "");
			String sql = "insert into user_info (NUM,PIC,NAME,PASSWORD,SEX,BIRTHDAY,PLACE,INFO)values (?,?,?,?,?,?,?,?)";
			String sql1 = "insert into friend (QQNUM,FRIEND)values(?,?)";
			// 读取已经注册的号码
			String sql2 = "select QQNUM from qqnum where ID=1";
			Statement stmt = null;
			ResultSet rs = null;
			try {
				stmt = con.createStatement();
				rs = stmt.executeQuery(sql2);
				rs.next();
				int qqnum = rs.getInt("QQNUM");
				PreparedStatement pre1 = con.prepareCall(sql);
				PreparedStatement pre3 = con.prepareCall(sql1);
				qqnum++;
				// 将注册的账号写入到好友表中
				pre3.setInt(1, qqnum);
				pre3.setString(2, null);
				pre3.executeUpdate();

				// 读取客户发来的信息
				String name = array[2];
				String password = array[3];
				String info = array[7];
				String pic = array[1];
				String sex = array[4];

				String place = array[6];
				String birthday = array[5];

				pre1.setInt(1, qqnum);
				pre1.setString(3, name);
				pre1.setString(4, password);
				pre1.setString(8, info);
				pre1.setString(2, pic);
				pre1.setString(5, sex);

				pre1.setString(7, place);
				pre1.setString(6, birthday);
				pre1.executeUpdate();

				// 更改qq号的语句
				String sql3 = "update QQNUM set QQNUM=? where ID=1";
				PreparedStatement pre2 = con.prepareCall(sql3);
				pre2.clearParameters();
				pre2.setInt(1, qqnum);
				pre2.executeUpdate();

				// 向用户端返回注册的号码
				dos.writeUTF(qqnum + "");
				rs.close();
				stmt.close();
			} catch (SQLException e) {
				try {
					e.printStackTrace();
					dos.writeUTF("fail");
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				e.printStackTrace();
			} catch (IOException e) {
				try {
					e.printStackTrace();
					dos.writeUTF("fail");
				} catch (IOException e1) {

					e1.printStackTrace();
				}
				e.printStackTrace();
			}
		}

		// 处理登陆事件的方法
		public void search(String array[]) throws SQLException, IOException {

			try {
				Class.forName("com.mysql.jdbc.Driver");
				String tableName1 = "select  PASSWORD from user_info where NUM=" + array[1];

				con = DriverManager.getConnection("jdbc:mysql://127.0.0.1/database?useUnicode=true&characterEncoding=utf8", "root", "");
				sta = con.createStatement();
				rs = sta.executeQuery(tableName1);

			} catch (ClassNotFoundException e) {
				dos.writeUTF("false");
				e.printStackTrace();
			} catch (SQLException e) {
				dos.writeUTF("false");
				e.printStackTrace();
			}
			try {
				rs.next();
				String s1 = rs.getString(1);

				if (s1.equals(array[2])) {

					String sql2 = "update user_info set STATUS=1  where NUM=" + array[1];
					String sql3 = "update user_info set IP='" + s.getInetAddress().getHostAddress() + "'"
							+ "  where NUM=" + array[1];
					String sql4 = "update user_info set PORT=" + array[3] + " where NUM=" + array[1];

					PreparedStatement ps2 = con.prepareStatement(sql2);
					ps2.executeUpdate();
					PreparedStatement ps3 = con.prepareStatement(sql3);
					ps3.executeUpdate();
					PreparedStatement ps4 = con.prepareStatement(sql4);
					ps4.executeUpdate();

					dos.writeUTF("true");
				} else {
					dos.writeUTF("false");
				}
			} catch (SQLException e) {
				e.printStackTrace();
				try {
					dos.writeUTF("false");
				} catch (IOException e1) {
				}
			} catch (IOException e) {

				e.printStackTrace();
			}
			sta.close();
			con.close();
		}
	}
}

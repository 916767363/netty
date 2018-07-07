package 时间服务器;

public class Main {
	public static void main(String[] args) throws Exception {
		int port = 18079;
		new TimeServer(port).run();
		/*
		 * rdate -o <port> -p <host>
		 * 
		 * Port 是你在main()函数中指定的端口，host 使用 localhost 就可以了。
		 */
	}
}

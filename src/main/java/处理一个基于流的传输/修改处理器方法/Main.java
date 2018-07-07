package 处理一个基于流的传输.修改处理器方法;

public class Main {
	public static void main(String[] args) throws Exception {
		String host = "127.0.0.1";
		int port = 18001;
		new TimeClient().start(host, port);
	}
}

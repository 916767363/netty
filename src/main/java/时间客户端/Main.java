package 时间客户端;

public class Main {
	public static void main(String[] args) throws Exception {
		String host = "127.0.0.1";
		int port = 18001;
		new TimeClient().start(host, port);
	}
}

package 应答服务器;

public class Main {
	public static void main(String[] args) throws Exception {
		// 绑定端口然后启动服务。这里我们在机器上绑定了机器所有网卡上的 13185 端口。
		// 当然现在你可以多次调用 bind()方法(基于不同绑定地址)
		int port;
		if (args.length > 0) {
			port = Integer.parseInt(args[0]);
		} else {
			port = 13185;
		}
		System.out.println(port);
		new EchoServerServer(port).run();
		// 测试： 在命令行上输入telnet localhost port
	}
}

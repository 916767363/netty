package 关闭应用;

import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;

public class Main {
	public static void main(String[] args) throws Exception {
		// TODO:这里只是说明shutdownGracefully是EventLoopGroup的方法
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		bossGroup.shutdownGracefully();
	}
}

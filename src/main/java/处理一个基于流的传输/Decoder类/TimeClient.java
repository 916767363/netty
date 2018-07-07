package 处理一个基于流的传输.Decoder类;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class TimeClient {

	public void start(String host, int port) {
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			// 在 Netty 中,编写服务端和客户端最大的并且唯一不同的使用了不同的BootStrap 和 Channel的实现
			Bootstrap b = new Bootstrap(); // (1)
			b.group(workerGroup); // (2)
			b.channel(NioSocketChannel.class); // (3)
			b.option(ChannelOption.SO_KEEPALIVE, true); // (4)
			b.handler(new ChannelInitializer<SocketChannel>() {
				@Override
				public void initChannel(SocketChannel ch) throws Exception {
					// 多个处理器
					ch.pipeline().addLast(new TimeDecoder(), new TimeClientHandler());
				}
			});
			// 启动客户端
			ChannelFuture f = b.connect(host, port).sync(); // (5)
			// 等待连接关闭
			f.channel().closeFuture().sync();
		} catch (Exception e) {

		} finally {
			workerGroup.shutdownGracefully();
		}
	}
}
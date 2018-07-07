package 时间服务器;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * 丢弃任何进入的数据
 */
public class TimeServer {

	private int port;

	public TimeServer(int port) {
		this.port = port;
	}

	public void run() throws Exception {
		/**
		 * 是用来处理I/O操作的多线程事件循环器， Netty 提供了许多不同的 EventLoopGroup
		 * 的实现用来处理不同的传输。在这个例子中我们实现了一个服务端的应用，因此会有2个 NioEventLoopGroup
		 * 会被使用。第一个经常被叫做‘boss’，用来接收进来的连接。第二个经常被叫做‘worker’，用来处理已经被接收的连接，一旦‘boss’
		 * 接收到连接，就会把连接信息注册到‘worker’上。如何知道多少个线程已经被使用，如何映射到已经创建的 Channel上都需要依赖于
		 * EventLoopGroup 的实现，并且可以通过构造函数来配置他们的关系
		 */
		EventLoopGroup bossGroup = new NioEventLoopGroup(); // (1) boss
		EventLoopGroup workerGroup = new NioEventLoopGroup(); // worker
		try {
			/*
			 * 一个启动 NIO 服务的辅助启动类。你可以在这个服务中直接使用
			 * Channel，但是这会是一个复杂的处理过程，在很多情况下你并不需要这样做
			 */
			ServerBootstrap b = new ServerBootstrap(); // (2)
			// 这里我们指定使用 NioServerSocketChannel 类来举例说明一个新的 Channel 如何接收进来的连接
			b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class) // (3)
					// ChannelInitializer 是一个特殊的处理类，他的目的是帮助使用者配置一个新的 Channel
					.childHandler(new ChannelInitializer<SocketChannel>() { // (4)
						@Override
						public void initChannel(SocketChannel ch) throws Exception {
							// 程序变的复杂时，可能你会增加更多的处理类到 pipline 上
							ch.pipeline().addLast(new TimeServerHandler());
						}
					})
					// option() 是提供给NioServerSocketChannel 用来接收进来的连接
					.option(ChannelOption.SO_BACKLOG, 128) // (5)
					// 是提供给由父管道 ServerChannel接收到的连接，例中是NioServerSocketChannel
					.childOption(ChannelOption.SO_KEEPALIVE, true); // (6)
			// 绑定端口，开始接收进来的连接
			ChannelFuture f = b.bind(port).sync(); // (7)

			// 等待服务器 socket 关闭 。
			// 在这个例子中，这不会发生，但你可以优雅地关闭你的服务器。
			f.channel().closeFuture().sync();
		} finally {
			workerGroup.shutdownGracefully();
			bossGroup.shutdownGracefully();
		}
	}
}
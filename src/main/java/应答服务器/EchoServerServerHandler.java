package 应答服务器;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class EchoServerServerHandler extends ChannelInboundHandlerAdapter {

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) {
		/*
		 * 调用了 write(Object) 方法来逐字地把接受到的消息写入。请注意不同于 DISCARD
		 * 的例子我们并没有释放接受到的消息，这是因为当写入的时候 Netty 已经帮我们释放了。 ctx.write(Object)
		 * 方法不会使消息写入到通道上，他被缓冲在了内部，你需要调用 ctx.flush() 方法来把缓冲区中数据强行输出。或者你可以用更简洁的
		 * cxt.writeAndFlush(msg) 以达到同样的目的
		 */
		ctx.write(msg); // (1)
		ctx.flush(); // (2)
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		// 当出现异常就关闭连接
		cause.printStackTrace();
		ctx.close();
	}
}
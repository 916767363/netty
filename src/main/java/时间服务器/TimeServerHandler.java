package 时间服务器;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * TIME 协议:在不接受任何请求时他会发送一个含32位的整数的消息，并且一旦消息发送就会立即关闭连接
 *
 */
public class TimeServerHandler extends ChannelInboundHandlerAdapter {
	/**
	 * channelActive： 会在连接被建立并且准备进行通信时被调用
	 */
	@Override
	public void channelActive(final ChannelHandlerContext ctx) { // (1)
		final ByteBuf time = ctx.alloc().buffer(4); // (2)分配缓冲区
		time.writeInt((int) (System.currentTimeMillis() / 1000L + 2208988800L));

		final ChannelFuture f = ctx.writeAndFlush(time); // (3)netty的操作是异步的，所以下面不能直接关闭,写出的是二进制数据
		f.addListener(new ChannelFutureListener() {// 使用监听，完成发送在执行关闭
			public void operationComplete(ChannelFuture future) {
				assert f == future;
				ctx.close();
			}
		});
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		cause.printStackTrace();
		ctx.close();
	}
}
package 抛弃服务器;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

/**
 * <p>
 * handler （处理器）是由 Netty 生成用来处理 I/O 事件的
 * <p>
 * ChannelInboundHandlerAdapter，这个类实现了
 * ChannelInboundHandler接口，ChannelInboundHandler 提供了许多事件处理的接口方法
 * </p>
 */
public class DiscardServerHandler extends ChannelInboundHandlerAdapter { // (1)

	/**
	 * 每当从客户端收到新的数据时，这个方法会在收到消息时被调用，这个例子中，收到的消息的类型是 ByteBuf
	 */
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) { // (2)
		ByteBuf in = (ByteBuf) msg;
		try {
			while (in.isReadable()) { // (1)
				System.out.print((char) in.readByte());
				System.out.flush();
			}
		} finally {
			ReferenceCountUtil.release(msg); // (2)
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) { // (4)
		// 当出现异常就关闭连接
		cause.printStackTrace();
		ctx.close();
	}
}
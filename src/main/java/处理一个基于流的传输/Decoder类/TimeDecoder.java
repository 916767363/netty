
package 处理一个基于流的传输.Decoder类;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

/**
 * <p>
 * 问题：由多个字段比如可变长度的字段组成的更为复杂的协议时，修改处理器的 方法将难以维护<br>
 * 方法：拆分成两个处理器 <br>
 * TimeDecoder 处理数据拆分的问题 <br>
 * TimeClientHandler 原始版本时间客户端的实现<br>
 * </p>
 * 
 * 说明：<br>
 * 1.ByteToMessageDecoder 是 ChannelInboundHandler 的一个实现类，他可以在处理数据拆分的问题上变得很简单。
 * 
 * 2.每当有新数据接收的时候，ByteToMessageDecoder 都会调用 decode() 方法来处理内部的那个累积缓冲。
 * 
 * 3.Decode()方法可以决定当累积缓冲里没有足够数据时可以往 out 对象里放任意数据。当有更多的数据被接收了
 * ByteToMessageDecoder 会再一次调用decode() 方法。 <br>
 * 
 * 4.如果在 decode() 方法里增加了一个对象到 out 对象里，这意味着解码器解码消息成功。ByteToMessageDecoder
 * 将会丢弃在累积缓冲里已经被读过的数据。请记得你不需要对多条消息调用 decode()，ByteToMessageDecoder 会持续调用
 * decode() 直到不放任何数据到 out 里。
 */
public class TimeDecoder extends ByteToMessageDecoder {
	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
		if (in.readableBytes() < 4) {
			return;
		}
		out.add(in.readBytes(4));
	}
}
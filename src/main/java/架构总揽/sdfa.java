package 架构总揽;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class sdfa {
	byte[] header;
	byte[] body;
	ByteBuf message = Unpooled.wrappedBuffer(header, body);
}

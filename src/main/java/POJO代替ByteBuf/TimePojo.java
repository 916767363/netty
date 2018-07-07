package POJO代替ByteBuf;

import java.util.Date;

/**
 * POJO
 * 
 * @author Administrator
 *
 */
public class TimePojo {

	private final long value;

	public TimePojo() {
		this(System.currentTimeMillis() / 1000L + 2208988800L);
	}

	public TimePojo(long value) {
		this.value = value;
	}

	public long value() {
		return value;
	}

	@Override
	public String toString() {
		return new Date((value() - 2208988800L) * 1000L).toString();
	}
}
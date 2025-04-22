import static java.nio.charset.StandardCharsets.UTF_8;

import java.util.Arrays;

public abstract class DfsStreamKey {
	public static DfsStreamKey of(String name) {
		return of(name.getBytes(UTF_8));
	}

	public static DfsStreamKey of(byte[] name) {
		return new ByteArrayDfsStreamKey(name);
	}

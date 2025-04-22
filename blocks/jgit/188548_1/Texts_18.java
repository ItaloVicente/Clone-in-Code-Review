package org.eclipse.jgit.internal.transport.sshd.agent.connector;

import java.nio.charset.Charset;

import com.sun.jna.Structure;
import com.sun.jna.Structure.FieldOrder;

public final class Sockets {

	private Sockets() {
	}


	public static final int AF_UNIX = 1;

	public static final int SOCK_STREAM = 1;

	public static final int DEFAULT_PROTOCOL = 0;

	@FieldOrder(value = { "sa_family"
	public static class SockAddr extends Structure {

		private static final int MAX_DATA_LENGTH = 108;

		public short sa_family = AF_UNIX;

		public byte[] sa_data = new byte[MAX_DATA_LENGTH];

		public SockAddr(String path
			byte[] bytes = path.getBytes(encoding);
			int toCopy = Math.min(sa_data.length - 1
			System.arraycopy(bytes
			sa_data[toCopy] = 0;
		}
	}
}

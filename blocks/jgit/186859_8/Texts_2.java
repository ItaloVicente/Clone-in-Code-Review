package org.eclipse.jgit.internal.transport.sshd.agent.connector;

import java.nio.charset.Charset;

import com.sun.jna.LastErrorException;
import com.sun.jna.Library;
import com.sun.jna.Structure;
import com.sun.jna.Structure.FieldOrder;
import com.sun.jna.platform.unix.LibCAPI;

interface Sockets extends LibCAPI


	int AF_UNIX = 1;

	int SOCK_STREAM = 1;

	int F_SETFD = 2;

	int FD_CLOEXEC = 1;

	int DEFAULT_PROTOCOL = 0;

	@FieldOrder(value = { "sa_family"
	class SockAddr extends Structure {

		private static final int MAX_DATA_LENGTH = 108;

		public short sa_family = AF_UNIX;

		public byte[] sa_data = new byte[MAX_DATA_LENGTH];

		public SockAddr(String data
			byte[] bytes = data.getBytes(encoding);
			int toCopy = Math.min(sa_data.length - 1
			System.arraycopy(bytes
			sa_data[toCopy] = 0;
		}
	}

	int socket(int domain

	int fcntl(int fd

	int connect(int fd

	LibCAPI.ssize_t read(int fd
			throws LastErrorException;

	LibCAPI.ssize_t write(int fd
			throws LastErrorException;
}

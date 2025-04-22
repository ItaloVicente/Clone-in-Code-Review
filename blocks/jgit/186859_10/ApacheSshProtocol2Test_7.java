package org.eclipse.jgit.internal.transport.sshd.agent.connector;

import com.sun.jna.LastErrorException;
import com.sun.jna.Library;
import com.sun.jna.Structure;
import com.sun.jna.platform.unix.LibCAPI;

interface UnixSockets extends LibCAPI


	int F_SETFD = 2;

	int FD_CLOEXEC = 1;

	int socket(int domain

	int fcntl(int fd

	int connect(int fd
			throws LastErrorException;

	LibCAPI.ssize_t read(int fd
			throws LastErrorException;

	LibCAPI.ssize_t write(int fd
			throws LastErrorException;
}

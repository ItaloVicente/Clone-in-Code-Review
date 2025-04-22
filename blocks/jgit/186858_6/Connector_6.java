package org.eclipse.jgit.transport.sshd.agent;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Objects;

import org.apache.sshd.agent.SshAgentConstants;
import org.apache.sshd.common.SshException;
import org.apache.sshd.common.util.buffer.BufferUtils;
import org.eclipse.jgit.internal.transport.sshd.SshdText;

public abstract class AbstractConnector implements Connector {

	private static final int MIN_REPLY_LENGTH = 8 * 1024;

	protected static final int DEFAULT_MAX_REPLY_LENGTH = 256 * 1024;

	private final int maxReplyLength;

	protected AbstractConnector() {
		this(DEFAULT_MAX_REPLY_LENGTH);
	}

	protected AbstractConnector(int maxReplyLength) {
		if (maxReplyLength < MIN_REPLY_LENGTH) {
			throw new IllegalArgumentException(
		}
		this.maxReplyLength = maxReplyLength;
	}

	protected int getMaximumMessageLength() {
		return this.maxReplyLength;
	}

	protected void prepareMessage(byte command
			throws IllegalArgumentException {
		Objects.requireNonNull(message);
		if (message.length < 5) {
					+ SshAgentConstants.getCommandMessageName(command)
					+ message.length);
		}
		BufferUtils.putUInt(message.length - 4
		message[4] = command;
	}

	protected int toLength(byte command
			throws IOException {
		long l = BufferUtils.getUInt(length);
		if (l <= 0 || l > maxReplyLength - 4) {
			throw new SshException(MessageFormat.format(
					SshdText.get().sshAgentReplyLengthError
					Long.toString(l)
					SshAgentConstants.getCommandMessageName(command)));
		}
		return (int) l;
	}
}

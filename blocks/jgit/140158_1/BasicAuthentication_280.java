package org.eclipse.jgit.internal.transport.sshd.auth;

import java.io.Closeable;

public interface AuthenticationHandler<ParameterType
		extends Closeable {

	void start() throws Exception;

	void process() throws Exception;

	void setParams(ParameterType input);

	TokenType getToken() throws Exception;

	boolean isDone();

	@Override
	void close();
}

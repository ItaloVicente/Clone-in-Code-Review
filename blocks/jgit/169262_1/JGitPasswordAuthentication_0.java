package org.eclipse.jgit.internal.transport.sshd;

import java.util.concurrent.CancellationException;

public class AuthenticationCanceledException extends CancellationException {


	private static final long serialVersionUID = 1L;

	public AuthenticationCanceledException() {
		super(SshdText.get().authenticationCanceled);
	}
}

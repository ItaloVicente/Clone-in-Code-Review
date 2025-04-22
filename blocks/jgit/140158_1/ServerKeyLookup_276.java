package org.eclipse.jgit.internal.transport.sshd;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.apache.sshd.common.config.keys.FilePasswordProvider;

public interface RepeatingFilePasswordProvider extends FilePasswordProvider {

	void setAttempts(int numberOfPasswordPrompts);

	default int getAttempts() {
		return 1;
	}


	public enum ResourceDecodeResult {
		TERMINATE
		RETRY
		IGNORE;
	}

	ResourceDecodeResult handleDecodeAttemptResult(String resourceKey
			String password
			throws IOException
}

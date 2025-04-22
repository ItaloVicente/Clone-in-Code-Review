package org.eclipse.jgit.internal.transport.sshd.proxy;

public class StatusLine {

	private final String version;

	private final int resultCode;

	private final String reason;

	public StatusLine(String version
		this.version = version;
		this.resultCode = resultCode;
		this.reason = reason;
	}

	public String getVersion() {
		return version;
	}

	public int getResultCode() {
		return resultCode;
	}

	public String getReason() {
		return reason;
	}
}


package org.eclipse.jgit.transport;

public class PushCertificate {

	protected String pusher;

	protected String pushee;

	protected NonceStatus nonceStatus;

	public enum NonceStatus {
		UNSOLICITED
		BAD
		MISSING
		OK
		SLOP
	}

	protected String commandList;

	protected String signature;

	public String getSignature() {
		return signature;
	}

	public String getCommandList() {
		return commandList;
	}

	public String getPusher() {
		return pusher;
	}

	public String getPushee() {
		return pushee;
	}

	public NonceStatus getNonceStatus() {
		return nonceStatus;
	}
}

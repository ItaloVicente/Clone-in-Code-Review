
package org.eclipse.jgit.transport;

public class PushCertificate {

	String pusher;

	String pushee;

	NonceStatus nonceStatus;

	public enum NonceStatus {
		UNSOLICITED
		BAD
		MISSING
		OK
		SLOP
	}

	String commandList;

	String signature;

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

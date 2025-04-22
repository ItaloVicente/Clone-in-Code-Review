
package org.eclipse.jgit.transport;

public class PushCertificate {

	protected String pusher;

	protected String pushee;

	protected String nonceStatus;

	public String getPusher() {
		return pusher;
	}

	public String getPushee() {
		return pushee;
	}

	public String getNonceStatus() {
		return nonceStatus;
	}
}

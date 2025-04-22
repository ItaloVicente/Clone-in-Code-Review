
package org.eclipse.jgit.transport;

public interface AdvertiseRefsHook {
	public static final AdvertiseRefsHook DEFAULT = new AdvertiseRefsHook() {
		public void advertiseRefs(UploadSession uploadSession) {
		}

		public void advertiseRefs(ReceiveSession receiveSession) {
		}
	};

	public void advertiseRefs(UploadSession uploadSession)
			throws ServiceMayNotContinueException;

	public void advertiseRefs(ReceiveSession receiveSession)
			throws ServiceMayNotContinueException;
}

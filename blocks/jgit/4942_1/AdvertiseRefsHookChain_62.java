
package org.eclipse.jgit.transport;

public interface AdvertiseRefsHook {
	public static final AdvertiseRefsHook DEFAULT = new AdvertiseRefsHook() {
		public void advertiseRefs(UploadPack uploadPack) {
		}

		public void advertiseRefs(BaseReceivePack receivePack) {
		}
	};

	public void advertiseRefs(UploadPack uploadPack)
			throws ServiceMayNotContinueException;

	public void advertiseRefs(BaseReceivePack receivePack)
			throws ServiceMayNotContinueException;
}

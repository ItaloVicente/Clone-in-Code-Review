
package org.eclipse.jgit.transport;

public interface AdvertiseRefsHook {
	AdvertiseRefsHook DEFAULT = new AdvertiseRefsHook() {
		@Override
		public void advertiseRefs(UploadPack uploadPack) {
		}

		@Override
		public void advertiseRefs(BaseReceivePack receivePack) {
		}
	};

	void advertiseRefs(UploadPack uploadPack)
			throws ServiceMayNotContinueException;

	void advertiseRefs(BaseReceivePack receivePack)
			throws ServiceMayNotContinueException;
}

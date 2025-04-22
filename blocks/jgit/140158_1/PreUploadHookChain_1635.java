
package org.eclipse.jgit.transport;

import java.util.Collection;

import org.eclipse.jgit.lib.ObjectId;

public interface PreUploadHook {
	PreUploadHook NULL = new PreUploadHook() {
		@Override
		public void onBeginNegotiateRound(UploadPack up
				Collection<? extends ObjectId> wants
				throws ServiceMayNotContinueException {
		}

		@Override
		public void onEndNegotiateRound(UploadPack up
				Collection<? extends ObjectId> wants
				int cntNotFound
				throws ServiceMayNotContinueException {
		}

		@Override
		public void onSendPack(UploadPack up
				Collection<? extends ObjectId> wants
				Collection<? extends ObjectId> haves)
				throws ServiceMayNotContinueException {
		}
	};

	void onBeginNegotiateRound(UploadPack up
			Collection<? extends ObjectId> wants
			throws ServiceMayNotContinueException;

	void onEndNegotiateRound(UploadPack up
			Collection<? extends ObjectId> wants
			int cntNotFound
			throws ServiceMayNotContinueException;

	void onSendPack(UploadPack up
			Collection<? extends ObjectId> haves)
			throws ServiceMayNotContinueException;
}

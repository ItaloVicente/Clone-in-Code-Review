
package org.eclipse.jgit.transport;

import java.util.Collection;

import org.eclipse.jgit.lib.ObjectId;

public interface PreUploadHook {
	public static final PreUploadHook NULL = new PreUploadHook() {
		public void onPreAdvertiseRefs(UploadPack up)
				throws UploadPackMayNotContinueException {
		}

		public void onBeginNegotiateRound(UploadPack up
				Collection<? extends ObjectId> wants
				throws UploadPackMayNotContinueException {
		}

		public void onEndNegotiateRound(UploadPack up
				Collection<? extends ObjectId> wants
				int cntNotFound
				throws UploadPackMayNotContinueException {
		}

		public void onSendPack(UploadPack up
				Collection<? extends ObjectId> wants
				Collection<? extends ObjectId> haves)
				throws UploadPackMayNotContinueException {
		}
	};

	public void onPreAdvertiseRefs(UploadPack up)
			throws UploadPackMayNotContinueException;

	public void onBeginNegotiateRound(UploadPack up
			Collection<? extends ObjectId> wants
			throws UploadPackMayNotContinueException;

	public void onEndNegotiateRound(UploadPack up
			Collection<? extends ObjectId> wants
			int cntNotFound
			throws UploadPackMayNotContinueException;

	public void onSendPack(UploadPack up
			Collection<? extends ObjectId> haves)
			throws UploadPackMayNotContinueException;
}

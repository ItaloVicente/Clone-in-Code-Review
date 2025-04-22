
package org.eclipse.jgit.transport;

import java.util.Collection;
import java.util.List;

import org.eclipse.jgit.lib.ObjectId;

public class PreUploadHookChain implements PreUploadHook {
	private final PreUploadHook[] hooks;

	public static PreUploadHook newChain(List<? extends PreUploadHook> hooks) {
		PreUploadHook[] newHooks = new PreUploadHook[hooks.size()];
		int i = 0;
		for (PreUploadHook hook : hooks) {
			if (hook != PreUploadHook.NULL)
				newHooks[i++] = hook;
		}
		if (i == 0)
			return PreUploadHook.NULL;
		else if (i == 1)
			return newHooks[0];
		else
			return new PreUploadHookChain(newHooks);
	}

	public void onPreAdvertiseRefs(UploadPack up)
			throws UploadPackMayNotContinueException {
		for (PreUploadHook hook : hooks) {
			hook.onPreAdvertiseRefs(up);
		}
	}

	public void onBeginNegotiateRound(UploadPack up
			Collection<? extends ObjectId> wants
			throws UploadPackMayNotContinueException {
		for (PreUploadHook hook : hooks) {
			hook.onBeginNegotiateRound(up
		}
	}

	public void onEndNegotiateRound(UploadPack up
			Collection<? extends ObjectId> wants
			int cntNotFound
			throws UploadPackMayNotContinueException {
		for (PreUploadHook hook : hooks) {
			hook.onEndNegotiateRound(up
		}
	}

	public void onSendPack(UploadPack up
			Collection<? extends ObjectId> wants
			Collection<? extends ObjectId> haves)
			throws UploadPackMayNotContinueException {
		for (PreUploadHook hook : hooks) {
			hook.onSendPack(up
		}
	}

	private PreUploadHookChain(PreUploadHook[] hooks) {
		this.hooks = hooks;
	}
}

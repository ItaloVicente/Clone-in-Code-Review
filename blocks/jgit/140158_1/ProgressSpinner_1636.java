
package org.eclipse.jgit.transport;

import java.util.Collection;
import java.util.List;

import org.eclipse.jgit.lib.ObjectId;

public class PreUploadHookChain implements PreUploadHook {
	private final PreUploadHook[] hooks;
	private final int count;

	public static PreUploadHook newChain(List<? extends PreUploadHook> hooks) {
		PreUploadHook[] newHooks = new PreUploadHook[hooks.size()];
		int i = 0;
		for (PreUploadHook hook : hooks)
			if (hook != PreUploadHook.NULL)
				newHooks[i++] = hook;
		if (i == 0)
			return PreUploadHook.NULL;
		else if (i == 1)
			return newHooks[0];
		else
			return new PreUploadHookChain(newHooks
	}

	@Override
	public void onBeginNegotiateRound(UploadPack up
			Collection<? extends ObjectId> wants
			throws ServiceMayNotContinueException {
		for (int i = 0; i < count; i++)
			hooks[i].onBeginNegotiateRound(up
	}

	@Override
	public void onEndNegotiateRound(UploadPack up
			Collection<? extends ObjectId> wants
			int cntNotFound
			throws ServiceMayNotContinueException {
		for (int i = 0; i < count; i++)
			hooks[i].onEndNegotiateRound(up
	}

	@Override
	public void onSendPack(UploadPack up
			Collection<? extends ObjectId> wants
			Collection<? extends ObjectId> haves)
			throws ServiceMayNotContinueException {
		for (int i = 0; i < count; i++)
			hooks[i].onSendPack(up
	}

	private PreUploadHookChain(PreUploadHook[] hooks
		this.hooks = hooks;
		this.count = count;
	}
}

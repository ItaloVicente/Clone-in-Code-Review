
package org.eclipse.jgit.transport;

import java.util.List;

public class AdvertiseRefsHookChain implements AdvertiseRefsHook {
	private final AdvertiseRefsHook[] hooks;
	private final int count;

	public static AdvertiseRefsHook newChain(List<? extends AdvertiseRefsHook> hooks) {
		AdvertiseRefsHook[] newHooks = new AdvertiseRefsHook[hooks.size()];
		int i = 0;
		for (AdvertiseRefsHook hook : hooks)
			if (hook != AdvertiseRefsHook.DEFAULT)
				newHooks[i++] = hook;
		if (i == 0)
			return AdvertiseRefsHook.DEFAULT;
		else if (i == 1)
			return newHooks[0];
		else
			return new AdvertiseRefsHookChain(newHooks
	}

	@Override
	public void advertiseRefs(BaseReceivePack rp)
			throws ServiceMayNotContinueException {
		for (int i = 0; i < count; i++)
			hooks[i].advertiseRefs(rp);
	}

	@Override
	public void advertiseRefs(UploadPack rp)
			throws ServiceMayNotContinueException {
		for (int i = 0; i < count; i++)
			hooks[i].advertiseRefs(rp);
	}

	private AdvertiseRefsHookChain(AdvertiseRefsHook[] hooks
		this.hooks = hooks;
		this.count = count;
	}
}

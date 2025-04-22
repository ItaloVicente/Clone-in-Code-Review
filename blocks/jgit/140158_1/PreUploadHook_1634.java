
package org.eclipse.jgit.transport;

import java.util.Collection;
import java.util.List;

public class PreReceiveHookChain implements PreReceiveHook {
	private final PreReceiveHook[] hooks;
	private final int count;

	public static PreReceiveHook newChain(List<? extends PreReceiveHook> hooks) {
		PreReceiveHook[] newHooks = new PreReceiveHook[hooks.size()];
		int i = 0;
		for (PreReceiveHook hook : hooks)
			if (hook != PreReceiveHook.NULL)
				newHooks[i++] = hook;
		if (i == 0)
			return PreReceiveHook.NULL;
		else if (i == 1)
			return newHooks[0];
		else
			return new PreReceiveHookChain(newHooks
	}

	@Override
	public void onPreReceive(ReceivePack rp
			Collection<ReceiveCommand> commands) {
		for (int i = 0; i < count; i++)
			hooks[i].onPreReceive(rp
	}

	private PreReceiveHookChain(PreReceiveHook[] hooks
		this.hooks = hooks;
		this.count = count;
	}
}

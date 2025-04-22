
package org.eclipse.jgit.transport;

import java.util.Collection;
import java.util.List;

public class PostReceiveHookChain implements PostReceiveHook {
	private final PostReceiveHook[] hooks;

	public static PostReceiveHook newChain(List<? extends PostReceiveHook> hooks) {
		PostReceiveHook[] newHooks = new PostReceiveHook[hooks.size()];
		int i = 0;
		for (PostReceiveHook hook : hooks) {
			if (hook != PostReceiveHook.NULL)
				newHooks[i++] = hook;
		}
		if (i == 0)
			return PostReceiveHook.NULL;
		else if (i == 1)
			return newHooks[0];
		else
			return new PostReceiveHookChain(newHooks);
	}

	public void onPostReceive(ReceivePack rp
		for (PostReceiveHook hook : hooks) {
			hook.onPostReceive(rp
		}
	}

	private PostReceiveHookChain(PostReceiveHook[] hooks) {
		this.hooks = hooks;
	}
}

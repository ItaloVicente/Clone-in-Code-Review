
package org.eclipse.jgit.transport;

import java.util.List;

import org.eclipse.jgit.storage.pack.PackStatistics;

public class PostUploadHookChain implements PostUploadHook {
	private final PostUploadHook[] hooks;
	private final int count;

	public static PostUploadHook newChain(List<? extends PostUploadHook> hooks) {
		PostUploadHook[] newHooks = new PostUploadHook[hooks.size()];
		int i = 0;
		for (PostUploadHook hook : hooks)
			if (hook != PostUploadHook.NULL)
				newHooks[i++] = hook;
		if (i == 0)
			return PostUploadHook.NULL;
		else if (i == 1)
			return newHooks[0];
		else
			return new PostUploadHookChain(newHooks
	}

	public void onPostUpload(PackStatistics stats) {
		for (int i = 0; i < count; i++)
			hooks[i].onPostUpload(stats);
	}

	private PostUploadHookChain(PostUploadHook[] hooks
		this.hooks = hooks;
		this.count = count;
	}
}

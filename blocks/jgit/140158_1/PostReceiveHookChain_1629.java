
package org.eclipse.jgit.transport;

import java.util.Collection;

public interface PostReceiveHook {
	PostReceiveHook NULL = new PostReceiveHook() {
		@Override
		public void onPostReceive(final ReceivePack rp
				final Collection<ReceiveCommand> commands) {
		}
	};

	void onPostReceive(ReceivePack rp
			Collection<ReceiveCommand> commands);
}

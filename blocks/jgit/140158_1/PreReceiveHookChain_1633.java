
package org.eclipse.jgit.transport;

import java.util.Collection;

public interface PreReceiveHook {
	PreReceiveHook NULL = new PreReceiveHook() {
		@Override
		public void onPreReceive(final ReceivePack rp
				final Collection<ReceiveCommand> commands) {
		}
	};

	void onPreReceive(ReceivePack rp
}

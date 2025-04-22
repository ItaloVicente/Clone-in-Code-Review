package org.eclipse.egit.ui.history;

import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.egit.ui.internal.history.GitHistoryPageSource;
import org.eclipse.team.ui.history.IHistoryPageSource;

public class GitHistoryAdapterFactory implements IAdapterFactory {

	@Override
	public <T> T getAdapter(Object adaptableObject, Class<T> adapterType) {
		if (adaptableObject != null
				&& adapterType == IHistoryPageSource.class) {
			return adapterType.cast(GitHistoryPageSource.INSTANCE);
		}
		return null;
	}

	@Override
	public Class<?>[] getAdapterList() {
		return new Class<?>[] { IHistoryPageSource.class };
	}
}

package org.eclipse.egit.ui.internal.history;

import java.io.IOException;

import org.eclipse.egit.ui.Activator;
import org.eclipse.egit.ui.internal.dialogs.CommitLabelProvider;

class GraphLabelProvider extends CommitLabelProvider {

	public GraphLabelProvider() {
		super();
	}

	public GraphLabelProvider(boolean canShowEmailAddresses) {
		super(canShowEmailAddresses);
	}

	@Override
	public String getColumnText(Object element, int columnIndex) {
		if (element == null) {
			return ""; //$NON-NLS-1$
		}
		final SWTCommit c = (SWTCommit) element;
		try {
			c.parseBody();
		} catch (IOException e) {
			Activator.error("Error parsing body", e); //$NON-NLS-1$
			return ""; //$NON-NLS-1$
		}
		return super.getColumnText(c, columnIndex);
	}
}

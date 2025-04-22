package org.eclipse.egit.ui.internal.properties;

import org.eclipse.egit.ui.Activator;
import org.eclipse.egit.ui.UIPreferences;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.ui.views.properties.PropertySheetPage;

public class GitPropertySheetPage extends PropertySheetPage {

	private final IPropertyChangeListener dateFormatListener = event -> {
		String property = event.getProperty();
		if (property == null) {
			return;
		}
		switch (property) {
		case UIPreferences.DATE_FORMAT:
		case UIPreferences.DATE_FORMAT_CHOICE:
			refreshInUiThread();
			break;
		default:
			break;
		}
	};

	private final IPreferenceStore store;

	private volatile boolean disposed;

	public GitPropertySheetPage() {
		super();
		store = Activator.getDefault().getPreferenceStore();
		store.addPropertyChangeListener(dateFormatListener);
	}

	public void refreshInUiThread() {
		getSite().getShell().getDisplay().asyncExec(() -> {
			if (!isDisposed()) {
				refresh();
			}
		});
	}

	public boolean isDisposed() {
		return disposed;
	}

	@Override
	public void dispose() {
		disposed = true;
		store.removePropertyChangeListener(dateFormatListener);
		super.dispose();
	}
}

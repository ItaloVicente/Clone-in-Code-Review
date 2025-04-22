package org.eclipse.egit.ui.internal.components;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.transport.RemoteConfig;
import org.eclipse.jgit.transport.URIish;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;

public class RemoteSelectionCombo extends Composite {

	public static interface IRemoteSelectionListener {
		void remoteSelected(RemoteConfig remoteConfig);
	}

	public static enum SelectionType {
		FETCH,
		PUSH
	}

	private static final int REMOTE_CONFIG_TEXT_MAX_LENGTH = 80;

	private SelectionType selectionType;

	private final Combo remoteCombo;

	private List<IRemoteSelectionListener> selectionListeners = new ArrayList<RemoteSelectionCombo.IRemoteSelectionListener>();

	private List<RemoteConfig> remoteConfigs;

	public RemoteSelectionCombo(Composite parent, int style,
			SelectionType selectionType) {
		super(parent, style);

		this.selectionType = selectionType;

		setLayout(new FillLayout());

		remoteCombo = new Combo(this, SWT.READ_ONLY | SWT.DROP_DOWN);
		remoteCombo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				RemoteConfig remoteConfig = getSelectedRemote();
				remoteSelected(remoteConfig);
			}
		});
	}

	public RemoteConfig setItems(List<RemoteConfig> remoteConfigs) {
		this.remoteConfigs = remoteConfigs;

		final String items[] = new String[remoteConfigs.size()];
		int i = 0;
		for (final RemoteConfig rc : remoteConfigs)
			items[i++] = getTextForRemoteConfig(rc);

		RemoteConfig defaultRemoteConfig = getDefaultRemoteConfig();
		final int defaultIndex = remoteConfigs.indexOf(defaultRemoteConfig);
		remoteCombo.setItems(items);
		remoteCombo.select(defaultIndex);

		return defaultRemoteConfig;
	}

	public void addRemoteSelectionListener(
			IRemoteSelectionListener selectionListener) {
		selectionListeners.add(selectionListener);
	}

	public RemoteConfig getSelectedRemote() {
		final int idx = remoteCombo.getSelectionIndex();
		if (remoteConfigs != null) {
			return remoteConfigs.get(idx);
		}
		return null;
	}

	@Override
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
		remoteCombo.setEnabled(enabled);
	}

	private RemoteConfig getDefaultRemoteConfig() {
		if (remoteConfigs == null || remoteConfigs.isEmpty())
			return null;
		for (final RemoteConfig rc : remoteConfigs)
			if (Constants.DEFAULT_REMOTE_NAME.equals(rc.getName()))
				return rc;
		return remoteConfigs.get(0);
	}

	private String getTextForRemoteConfig(final RemoteConfig rc) {
		final StringBuilder sb = new StringBuilder(rc.getName());
		sb.append(": "); //$NON-NLS-1$
		boolean first = true;
		List<URIish> uris;
		if (selectionType == SelectionType.FETCH)
			uris = rc.getURIs();
		else {
			uris = rc.getPushURIs();
			if (uris.isEmpty())
				uris = rc.getURIs();
		}

		for (final URIish u : uris) {
			final String uString = u.toString();
			if (first)
				first = false;
			else {
				sb.append(", "); //$NON-NLS-1$
				if (sb.length() + uString.length() > REMOTE_CONFIG_TEXT_MAX_LENGTH) {
					sb.append("..."); //$NON-NLS-1$
					break;
				}
			}
			sb.append(uString);
		}
		return sb.toString();
	}

	private void remoteSelected(RemoteConfig remoteConfig) {
		for (IRemoteSelectionListener listener : selectionListeners)
			listener.remoteSelected(remoteConfig);
	}
}

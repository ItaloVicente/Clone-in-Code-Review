package org.eclipse.ui.internal.statushandlers;

import java.util.Map;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.statushandlers.AbstractStatusAreaProvider;
import org.eclipse.ui.statushandlers.StatusAdapter;


public class DetailsAreaManager {

	private Map dialogState;
	private Control control = null;

	public DetailsAreaManager(Map dialogState) {
		this.dialogState = dialogState;
	}

	public void close() {
		if (control != null && !control.isDisposed()) {
			control.dispose();
		}
	}

	public void createDetailsArea(Composite parent,
			StatusAdapter statusAdapter) {
		Composite container = new Composite(parent, SWT.NONE);
		container.setLayout(GridLayoutFactory.fillDefaults().create());
		container.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		getProvider().createSupportArea(container, statusAdapter);
		control = container;
	}

	public AbstractStatusAreaProvider getProvider() {
		AbstractStatusAreaProvider provider = (AbstractStatusAreaProvider) dialogState
				.get(IStatusDialogConstants.CUSTOM_DETAILS_PROVIDER);
		if (provider == null) {
			provider = new DefaultDetailsArea(dialogState);
		}
		return provider;
	}

	public boolean isOpen() {
		if (control == null || control.isDisposed()) {
			return false;
		}
		return true;
	}
}

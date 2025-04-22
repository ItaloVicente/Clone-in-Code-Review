package org.eclipse.egit.ui.internal.repository;

import java.io.File;
import java.io.IOException;

import org.eclipse.egit.ui.Activator;
import org.eclipse.egit.ui.internal.preferences.ConfigurationEditorComponent;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.StoredConfig;
import org.eclipse.jgit.storage.file.FileBasedConfig;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.dialogs.PropertyPage;

public class RepositoryPropertyPage extends PropertyPage {

	private ConfigurationEditorComponent editor;

	protected Control createContents(Composite parent) {
		Repository repo = (Repository) getElement()
				.getAdapter(Repository.class);
		StoredConfig config = repo.getConfig();
		if (config instanceof FileBasedConfig) {
			File configFile = ((FileBasedConfig) config).getFile();
			config = new FileBasedConfig(configFile, repo.getFS());
		}
		Composite displayArea = new Composite(parent, SWT.NONE);
		GridLayoutFactory.fillDefaults().applyTo(displayArea);
		GridDataFactory.fillDefaults().applyTo(displayArea);
		editor = new ConfigurationEditorComponent(displayArea, config, true) {
			@Override
			protected void setErrorMessage(String message) {
				RepositoryPropertyPage.this.setErrorMessage(message);
			}
		};
		editor.createContents();
		return displayArea;
	}

	protected void performDefaults() {
		try {
			editor.restore();
		} catch (IOException e) {
			Activator.handleError(e.getMessage(), e, true);
		}
		super.performDefaults();
	}

	public boolean performOk() {
		try {
			editor.save();
		} catch (IOException e) {
			Activator.handleError(e.getMessage(), e, true);
		}
		return super.performOk();
	}
}

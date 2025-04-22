package org.eclipse.ui.internal.wizards.preferences;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.IPreferenceFilter;
import org.eclipse.core.runtime.preferences.IPreferencesService;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.internal.WorkbenchPlugin;
import org.eclipse.ui.internal.preferences.PreferenceTransferElement;

public class WizardPreferencesExportPage1 extends WizardPreferencesPage  {

	private static final String PREFERENCESEXPORTPAGE1 = "preferencesExportPage1"; // //$NON-NLS-1$

	protected WizardPreferencesExportPage1(String name) {
		super(name);
		setTitle(PreferencesMessages.WizardPreferencesExportPage1_exportTitle);
		setDescription(PreferencesMessages.WizardPreferencesExportPage1_exportDescription);
	}

	public WizardPreferencesExportPage1() {
		this(PREFERENCESEXPORTPAGE1);
	}

	protected String getOutputSuffix() {
    	return ".epf"; //$NON-NLS-1$
    }
	
	@Override
	protected String getDestinationValue() {
		String idealSuffix = getOutputSuffix();
        String destinationText = super.getDestinationValue();

        if (destinationText.length() != 0
                && !destinationText.endsWith(File.separator)) {
            int dotIndex = destinationText.lastIndexOf('.');
            if (dotIndex != -1) {
                int pathSepIndex = destinationText.lastIndexOf(File.separator);
                if (pathSepIndex != -1 && dotIndex < pathSepIndex) {
                    destinationText += idealSuffix;
                }
            } else {
                destinationText += idealSuffix;
            }
        }

        return destinationText;
    }

		
	@Override
	protected String getAllButtonText() {
		return PreferencesMessages.WizardPreferencesExportPage1_all;
	}

	@Override
	protected String getChooseButtonText() {
		return PreferencesMessages.WizardPreferencesExportPage1_choose;
	}

	@Override
	protected void createTransferArea(Composite composite) {
		createTransfersList(composite);
		createDestinationGroup(composite);
		createOptionsGroup(composite);
	}

	@Override
	protected String getDestinationLabel() {
		return PreferencesMessages.WizardPreferencesExportPage1_file;
	}

	@Override
	protected PreferenceTransferElement[] getTransfers() {
		PreferenceTransferElement[] elements = super.getTransfers();
		PreferenceTransferElement[] returnElements = new PreferenceTransferElement[elements.length];
		IPreferenceFilter[] filters = new IPreferenceFilter[1];
		IPreferenceFilter[] matches;
		IPreferencesService service = Platform.getPreferencesService();
		int count = 0;
		try {
			for (int i = 0; i < elements.length; i++) {
				PreferenceTransferElement element = elements[i];
				filters[0] = element.getFilter();
				matches = service.matches((IEclipsePreferences) service
						.getRootNode().node("instance"), filters); //$NON-NLS-1$
				if (matches.length > 0) {
					returnElements[count++] = element;
				}
			}
			elements = new PreferenceTransferElement[count];
			System.arraycopy(returnElements, 0, elements, 0, count);
		} catch (CoreException e) {
			WorkbenchPlugin.log(e.getMessage(), e);
			return new PreferenceTransferElement[0];
		}
		return elements;
	}

	@Override
	protected boolean transfer(IPreferenceFilter[] transfers) {
		File exportFile = new File(getDestinationValue());
		if (!ensureTargetIsValid(exportFile)) {
			return false;
		}
		FileOutputStream fos = null;
		try {
			if (transfers.length > 0) {
				try {
					fos = new FileOutputStream(exportFile);
				} catch (FileNotFoundException e) {
					WorkbenchPlugin.log(e.getMessage(), e);
					MessageDialog.open(MessageDialog.ERROR, getControl()
							.getShell(), new String(), e.getLocalizedMessage(),
							SWT.SHEET);
					return false;
				}
				IPreferencesService service = Platform.getPreferencesService();
				try {
					service.exportPreferences(service.getRootNode(), transfers,
							fos);
				} catch (CoreException e) {
					WorkbenchPlugin.log(e.getMessage(), e);
					MessageDialog.open(MessageDialog.ERROR, getControl()
							.getShell(), new String(), e.getLocalizedMessage(),
							SWT.SHEET);
					return false;
				}
			}
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					WorkbenchPlugin.log(e.getMessage(), e);
					MessageDialog.open(MessageDialog.ERROR, getControl()
							.getShell(), new String(), e.getLocalizedMessage(),
							SWT.SHEET);
					return false;
				}
			}
		}
		return true;
	}

	@Override
	protected String getFileDialogTitle() {
		return PreferencesMessages.WizardPreferencesExportPage1_title;
	}

	@Override
	protected int getFileDialogStyle() {
		return SWT.SAVE | SWT.SHEET;
	}
	
	@Override
	protected String getInvalidDestinationMessage() {
		return PreferencesMessages.WizardPreferencesExportPage1_noPrefFile;
	}

	@Override
	protected boolean shouldSaveTransferAll() {
		return true;
	}
}

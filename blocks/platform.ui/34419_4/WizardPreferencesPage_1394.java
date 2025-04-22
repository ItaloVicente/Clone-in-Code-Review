package org.eclipse.ui.internal.wizards.preferences;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.preferences.IExportedPreferences;
import org.eclipse.core.runtime.preferences.IPreferenceFilter;
import org.eclipse.core.runtime.preferences.IPreferencesService;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.ui.internal.WorkbenchPlugin;
import org.eclipse.ui.internal.preferences.PreferenceTransferElement;

public class WizardPreferencesImportPage1 extends WizardPreferencesPage {

    protected WizardPreferencesImportPage1(String pageName) {
        super(pageName);
        setTitle(PreferencesMessages.WizardPreferencesImportPage1_importTitle);
        setDescription(PreferencesMessages.WizardPreferencesImportPage1_importDescription);
    }

    public WizardPreferencesImportPage1() {
        this("preferencesImportPage1");//$NON-NLS-1$
    }

    @Override
	protected String getAllButtonText() {
        return PreferencesMessages.WizardPreferencesImportPage1_all;
    }

    @Override
	protected String getChooseButtonText() {
        return PreferencesMessages.WizardPreferencesImportPage1_choose;
    }

   
    @Override
	protected PreferenceTransferElement[] getTransfers() {
        if (validFromFile()) {
            FileInputStream fis;

            try {
                fis = new FileInputStream(getDestinationValue());
            } catch (FileNotFoundException e) {
                WorkbenchPlugin.log(e.getMessage(), e);
                return new PreferenceTransferElement[0];
            }
            IPreferencesService service = Platform.getPreferencesService();
            try {
                IExportedPreferences prefs;
                prefs = service.readPreferences(fis);
                PreferenceTransferElement[] transfers = super.getTransfers();
                IPreferenceFilter[] filters = new IPreferenceFilter[transfers.length];
                for (int i = 0; i < transfers.length; i++) {
                    PreferenceTransferElement transfer = transfers[i];
                    filters[i] = transfer.getFilter();
                }
                IPreferenceFilter[] matches = service.matches(prefs, filters);
                PreferenceTransferElement[] returnTransfers = new PreferenceTransferElement[matches.length];
                int index = 0;
                for (int i = 0; i < matches.length; i++) {
                    IPreferenceFilter filter = matches[i];
                    for (int j = 0; j < transfers.length; j++) {
                        PreferenceTransferElement element = transfers[j];
                        if (element.getFilter().equals(filter)) {
							returnTransfers[index++] = element;
						}                        
                    }
                }

                PreferenceTransferElement[] destTransfers = new PreferenceTransferElement[index];
                System.arraycopy(returnTransfers, 0, destTransfers, 0, index);
                return destTransfers;
            } catch (CoreException e) {
            } finally {
                try {
                    fis.close();
                } catch (IOException e) {
                    WorkbenchPlugin.log(e.getMessage(), e);
                }
            }
        }

        return new PreferenceTransferElement[0];
    }

    private boolean validFromFile() {
        File fromFile = new File(getDestinationValue());
        return fromFile.exists() && !fromFile.isDirectory();
    }

    @Override
	protected void setPreferenceTransfers() {
    	super.setPreferenceTransfers();	
    	
		if (validFromFile()
				&& (transfersTree.getViewer().getTree().getItemCount() == 0)) {
			descText
					.setText(PreferencesMessages.WizardPreferences_noSpecificPreferenceDescription);
		} else {
			descText.setText(""); //$NON-NLS-1$
		}
	}
  
    @Override
	protected void createTransferArea(Composite composite) {
        createDestinationGroup(composite);
        createTransfersList(composite);
    }

    @Override
	protected String getDestinationLabel() {
        return PreferencesMessages.WizardPreferencesImportPage1_file;
    }

    @Override
	protected boolean transfer(IPreferenceFilter[] filters) {
        File importFile = new File(getDestinationValue());
        FileInputStream fis = null;
        try {
            if (filters.length > 0) {
                try {
                    fis = new FileInputStream(importFile);
                } catch (FileNotFoundException e) {
                    WorkbenchPlugin.log(e.getMessage(), e);
					MessageDialog.open(MessageDialog.ERROR, getControl()
							.getShell(), new String(), e.getLocalizedMessage(),
							SWT.SHEET);
                    return false;
                }
                IPreferencesService service = Platform.getPreferencesService();
                try {
                    IExportedPreferences prefs = service.readPreferences(fis);
                    
                    service.applyPreferences(prefs, filters);
                } catch (CoreException e) {
                    WorkbenchPlugin.log(e.getMessage(), e);
					MessageDialog.open(MessageDialog.ERROR, getControl()
							.getShell(), new String(), e.getLocalizedMessage(),
							SWT.SHEET);
                    return false;
                }
            }
        } finally {
            if (fis != null) {
				try {
                    fis.close();
                } catch (IOException e) {
                	WorkbenchPlugin.log(e.getMessage(), e);
					MessageDialog.open(MessageDialog.ERROR, getControl()
							.getShell(), new String(), e.getLocalizedMessage(),
							SWT.SHEET);
                }
			}
        }
        return true;
    }

    @Override
	public void handleEvent(Event e) {
        if (e.widget == destinationNameField) {
			setPreferenceTransfers();
		}

        super.handleEvent(e);
    }
	
    @Override
	protected String getFileDialogTitle(){
		return PreferencesMessages.WizardPreferencesImportPage1_title;
	}
	
	@Override
	protected int getFileDialogStyle() {
		return SWT.OPEN | SWT.SHEET;
	}
	
	@Override
	protected boolean validDestination() {
		return super.validDestination() && validFromFile();
	}

	@Override
	protected String getInvalidDestinationMessage() {
		return PreferencesMessages.WizardPreferencesImportPage1_invalidPrefFile;
	}

	@Override
	protected boolean shouldSaveTransferAll() {
		return false;
	}
}

package org.eclipse.egit.ui.internal.dialogs;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.egit.ui.UIText;
import org.eclipse.jface.dialogs.TrayDialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jgit.transport.CredentialItem;
import org.eclipse.jgit.transport.CredentialItem.CharArrayType;
import org.eclipse.jgit.transport.CredentialItem.StringType;
import org.eclipse.jgit.transport.CredentialItem.YesNoType;
import org.eclipse.jgit.transport.URIish;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class CustomPromptDialog extends TrayDialog {
	private CredentialItem[] credentialItems;
	private List<Control> editingControls;
	private URIish uri;
	private String title;
	private static final String KEY_ITEM = "item"; //$NON-NLS-1$

	public CustomPromptDialog(Shell shell, URIish uri, String title, CredentialItem... items) {
		super(shell);
		this.uri = uri;
		this.title = title; 
		setShellStyle(getShellStyle() | SWT.SHELL_TRIM);
		credentialItems = items;
	}
	
	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		if (title != null) {
			newShell.setText(title);
		}
	}
	
	public CredentialItem[] getCredentialItems() {
		return credentialItems;
	}
	
	@Override
	protected Control createDialogArea(Composite parent) {
		editingControls = new ArrayList<Control>(credentialItems.length);
		
		Composite main = (Composite) super.createDialogArea(parent);
		GridLayout mainLayout = (GridLayout) main.getLayout();
		mainLayout.numColumns = 2;

		Label infoLabel = new Label(main, SWT.NONE);
		GridDataFactory.defaultsFor(infoLabel).span(2, 1).applyTo(infoLabel);
		String tempInfoText = hasEditingItems() 
				? UIText.CustomPromptDialog_provide_information_for
				: UIText.CustomPromptDialog_information_about;
		infoLabel.setText(NLS.bind(tempInfoText, uri.toString()));
		
		for (CredentialItem item : credentialItems) {
			Label label = new Label(main, SWT.NONE);
			label.setText(item.getPromptText());
			GridDataFactory.defaultsFor(label).applyTo(label);
			
			if (item instanceof CharArrayType || item instanceof StringType) {
				Text text = new Text(main, SWT.BORDER | (item.isValueSecure() ? SWT.PASSWORD : SWT.NONE));
				GridDataFactory.defaultsFor(text).applyTo(text);
				text.setData(KEY_ITEM, item);
				editingControls.add(text);
			} else if (item instanceof YesNoType) {
				Button checkBox = new Button(main, SWT.CHECK);
				GridDataFactory.defaultsFor(checkBox).applyTo(checkBox);
				editingControls.add(checkBox);
			} else {
				Label dummy = new Label(main, SWT.NONE);
				GridDataFactory.fillDefaults().applyTo(dummy);
			}
		}
		
		return main;
	}

	private boolean hasEditingItems() {
		for (CredentialItem item : credentialItems) {
			if (item instanceof StringType) {
				return true;
			}
			if (item instanceof CharArrayType) {
				return true;
			}
			if (item instanceof YesNoType) {
				return true;
			}
		}
		return false;
	}

	@Override
	protected void okPressed() {
		updateValues();
		
		super.okPressed();
	}

	private void updateValues() {
		for (Control control : editingControls) {
			if (control instanceof Text) {
				Text textControl = (Text) control;
				CredentialItem itemToUpdate = (CredentialItem) textControl.getData(KEY_ITEM);
				String value = textControl.getText();
				updateValue(itemToUpdate, value);
			} else if (control instanceof Button) {
				Button checkBoxControl = (Button) control;
				CredentialItem itemToUpdate = (CredentialItem) checkBoxControl.getData(KEY_ITEM);
				boolean value = checkBoxControl.getSelection();
				updateValue(itemToUpdate, value);
			}
		}
	}

	protected void updateValue(CredentialItem itemToUpdate, String value) {
		if (itemToUpdate instanceof CharArrayType) {
			((CharArrayType) itemToUpdate).setValueNoCopy(value.toCharArray());
		} else if (itemToUpdate instanceof StringType) {
			((StringType) itemToUpdate).setValue(value);
		} else {
			throw new IllegalArgumentException("Cannot handle item of type " + itemToUpdate.getClass()); //$NON-NLS-1$
		}
	}
	
	protected void updateValue(CredentialItem itemToUpdate, boolean value) {
		if (itemToUpdate instanceof YesNoType) {
			((YesNoType) itemToUpdate).setValue(value);
		} else {
			throw new IllegalArgumentException("Cannot handle item of type " + itemToUpdate.getClass()); //$NON-NLS-1$
		}
	}
}

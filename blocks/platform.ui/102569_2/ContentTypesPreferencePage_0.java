package org.eclipse.ui.internal.dialogs;

import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.layout.LayoutConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.WorkbenchMessages;
import org.eclipse.ui.internal.WorkbenchPlugin;

public class ContentTypeFilenameAssociationDialog extends TitleAreaDialog {

	private static final String DIALOG_SETTINGS_SECTION = "ContentTypeFilenameAssociationDialog"; //$NON-NLS-1$

    private String filename = ""; //$NON-NLS-1$

    private String initialValue;

    private Text filenameField;

    private Button okButton;

	private String title;

	private String helpContextId;

	private final String headerTitle;

	private final String message2;

	private final String label;

	private int specType;

    public ContentTypeFilenameAssociationDialog(Shell parentShell, String title, String helpContextId, String headerTitle, String message, String label) {
    	super(parentShell);
    	this.title = title;
    	this.helpContextId = helpContextId;
		this.headerTitle = headerTitle;
		message2 = message;
		this.label = label;

		setShellStyle(getShellStyle() | SWT.SHEET);
    }

    @Override
	protected void configureShell(Shell shell) {
        super.configureShell(shell);
        shell.setText(title);
        PlatformUI.getWorkbench().getHelpSystem().setHelp(shell, helpContextId);
    }


    @Override
	protected Control createDialogArea(Composite parent) {
		Composite parentComposite = (Composite) super.createDialogArea(parent);

		Composite contents = new Composite(parentComposite, SWT.NONE);
		contents.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		setTitle(headerTitle);
		setMessage(message2);

		new Label(contents, SWT.LEFT)
				.setText(label);

		filenameField = new Text(contents, SWT.SINGLE | SWT.BORDER);
		if (initialValue != null) {
			filenameField.setText(initialValue);
		}
		filenameField.addModifyListener(event -> {
			if (event.widget == filenameField) {
				filename = filenameField.getText().trim();
				okButton.setEnabled(validateFileType());
			}
		});
		filenameField.setFocus();

		Dialog.applyDialogFont(parentComposite);

		Point defaultMargins = LayoutConstants.getMargins();
		GridLayoutFactory.fillDefaults().numColumns(2).margins(
				defaultMargins.x, defaultMargins.y).generateLayout(contents);

		return contents;
	}


    @Override
	protected void createButtonsForButtonBar(Composite parent) {
        okButton = createButton(parent, IDialogConstants.OK_ID,
                IDialogConstants.OK_LABEL, true);
        okButton.setEnabled(false);
        createButton(parent, IDialogConstants.CANCEL_ID,
                IDialogConstants.CANCEL_LABEL, false);
    }

    private boolean validateFileType() {

        if (filename.length() == 0) {
            setErrorMessage(null);
            return false;
        }

        int index = filename.lastIndexOf('.');
        if (index == filename.length() - 1) {
            if (index == 0 || (index == 1 && filename.charAt(0) == '*')) {
                setErrorMessage(WorkbenchMessages.FileExtension_extensionEmptyMessage);
                return false;
            }
        }

        index = filename.indexOf('*');
        if (index > -1) {
            if (filename.length() == 1) {
                setErrorMessage(WorkbenchMessages.FileExtension_extensionEmptyMessage);
                return false;
            }
        }

		if (hasWildcards(filename)) {
			this.specType = IContentType.FILE_PATTERN_SPEC; // start with most general, then refine
			int extPrefix = filename.indexOf("*."); //$NON-NLS-1$
			if (extPrefix == 0) {
				String ext = filename.substring(2);
				if (!hasWildcards(ext)) {
					this.specType = IContentType.FILE_EXTENSION_SPEC;
				}
			}
		} else {
			this.specType = IContentType.FILE_NAME_SPEC;
		}
        setErrorMessage(null);
        return true;
    }

	private static boolean hasWildcards(String s) {
		return s.contains("?") || s.contains("*"); //$NON-NLS-1$ //$NON-NLS-2$
	}

    public void setInitialValue(String initialValue) {
    	this.initialValue = initialValue;
    }

    @Override
	protected IDialogSettings getDialogBoundsSettings() {
        IDialogSettings settings = WorkbenchPlugin.getDefault().getDialogSettings();
        IDialogSettings section = settings.getSection(DIALOG_SETTINGS_SECTION);
        if (section == null) section = settings.addNewSection(DIALOG_SETTINGS_SECTION);
        return section;
    }

    @Override
	protected boolean isResizable() {
    	return true;
    }

	public String getSpecText() {
		if (this.specType == IContentType.FILE_EXTENSION_SPEC) {
			return filename.substring(2);
		}
		return filename;
	}

	public int getSpecType() {
		return this.specType;
	}
}

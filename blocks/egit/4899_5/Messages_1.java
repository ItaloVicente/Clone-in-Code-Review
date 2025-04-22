package org.eclipse.egit.internal.importing;

import java.net.URI;

import org.eclipse.egit.core.internal.GitURI;
import org.eclipse.egit.ui.internal.SWTUtils;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.StyledCellLabelProvider;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.team.core.ScmUrlImportDescription;
import org.eclipse.team.ui.IScmUrlImportWizardPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;

public class GitScmUrlImportWizardPage extends WizardPage implements
		IScmUrlImportWizardPage {

	private ScmUrlImportDescription[] descriptions;
	private Label counterLabel;
	private TableViewer bundlesViewer;
	private Button useMaster;

	private static final String GIT_PAGE_USE_MASTER = "org.eclipse.team.egit.ui.import.page.master"; //$NON-NLS-1$

	class GitLabelProvider extends StyledCellLabelProvider implements ILabelProvider {

		public Image getImage(Object element) {
			return PlatformUI.getWorkbench().getSharedImages().getImage(IDE.SharedImages.IMG_OBJ_PROJECT);
		}

		public String getText(Object element) {
			return getStyledText(element).getString();
		}

		public void update(ViewerCell cell) {
			StyledString string = getStyledText(cell.getElement());
			cell.setText(string.getString());
			cell.setStyleRanges(string.getStyleRanges());
			cell.setImage(getImage(cell.getElement()));
			super.update(cell);
		}

		private StyledString getStyledText(Object element) {
			StyledString styledString = new StyledString();
			if (element instanceof ScmUrlImportDescription) {
				ScmUrlImportDescription description = (ScmUrlImportDescription) element;
				String project = description.getProject();
				URI scmUrl = description.getUri();
				String version = getTag(scmUrl);
				String host = getServer(scmUrl);
				styledString.append(project);
				if (version != null) {
					styledString.append(' ');
					styledString.append(version, StyledString.DECORATIONS_STYLER);
				}
				styledString.append(' ');
				styledString.append('[', StyledString.DECORATIONS_STYLER);
				styledString.append(host, StyledString.DECORATIONS_STYLER);
				styledString.append(']', StyledString.DECORATIONS_STYLER);
				return styledString;
			}
			styledString.append(element.toString());
			return styledString;
		}
	}

	public GitScmUrlImportWizardPage() {
		super("git", Messages.GitScmUrlImportWizardPage_title, null); //$NON-NLS-1$
		setDescription(Messages.GitScmUrlImportWizardPage_description);
	}

	public void createControl(Composite parent) {
		Composite comp = SWTUtils.createHVFillComposite(parent, SWTUtils.MARGINS_NONE, 1);
		Composite group = SWTUtils.createHFillComposite(comp, SWTUtils.MARGINS_NONE, 1);

		Button versions = SWTUtils.createRadioButton(group, Messages.GitScmUrlImportWizardPage_importVersion);
		useMaster = SWTUtils.createRadioButton(group, Messages.GitScmUrlImportWizardPage_importMaster);
		SelectionListener listener = new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				bundlesViewer.refresh(true);
			}
		};
		versions.addSelectionListener(listener);
		useMaster.addSelectionListener(listener);

		Table table = new Table(comp, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL);
		GridData gd = new GridData(GridData.FILL_BOTH);
		gd.heightHint = 200;
		gd.widthHint = 225;
		table.setLayoutData(gd);

		bundlesViewer = new TableViewer(table);
		bundlesViewer.setLabelProvider(new GitLabelProvider());
		bundlesViewer.setContentProvider(new ArrayContentProvider());
		bundlesViewer.setComparator(new ViewerComparator());
		counterLabel = new Label(comp, SWT.NONE);
		counterLabel.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		setControl(comp);
		setPageComplete(true);

		useMaster.setSelection(true);
		versions.setEnabled(false);

		if (descriptions != null) {
			bundlesViewer.setInput(descriptions);
			updateCount();
		}

	}

	public boolean finish() {
		boolean head = false;
		if (getControl() != null) {
			head = useMaster.getSelection();
			IDialogSettings settings = getWizard().getDialogSettings();
			if (settings != null)
				settings.put(GIT_PAGE_USE_MASTER, head);
		} else {
			IDialogSettings settings = getWizard().getDialogSettings();
			if (settings != null)
				head = settings.getBoolean(GIT_PAGE_USE_MASTER);
		}

		if (head)
			for (int i = 0; i < descriptions.length; i++) {
				URI scmUri = descriptions[i].getUri();
				descriptions[i].setUrl(removeTag(scmUri));
			}

		return true;
	}

	public ScmUrlImportDescription[] getSelection() {
		return descriptions;
	}

	public void setSelection(ScmUrlImportDescription[] descriptions) {
		this.descriptions = descriptions;
		if (bundlesViewer != null) {
			bundlesViewer.setInput(descriptions);
			updateCount();
		}
	}

	private void updateCount() {
		counterLabel.setText(NLS.bind(Messages.GitScmUrlImportWizardPage_counter, new Integer(descriptions.length)));
		counterLabel.getParent().layout();
	}

	private static String getTag(URI scmUri) {
		GitURI gitURI = new GitURI(scmUri);
		return gitURI.getTag();
	}

	private static String removeTag(URI scmUri) {
		StringBuffer sb = new StringBuffer();
		sb.append(scmUri.getScheme()).append(':');
		String ssp = scmUri.getSchemeSpecificPart();
		int j = ssp.indexOf(';');
		if (j != -1) {
			sb.append(ssp.substring(0, j));
			String[] params = ssp.substring(j).split(";"); //$NON-NLS-1$
			for (int k = 0; k < params.length; k++)
				if (params[k].startsWith("tag=")) { //$NON-NLS-1$
				} else if (params[k].startsWith("version=")) { //$NON-NLS-1$
				} else if (params[k]!=null && !params[k].equals("")) //$NON-NLS-1$
					sb.append(";").append(params[k]); //$NON-NLS-1$
		} else
			sb.append(ssp);
		return sb.toString();
	}

	private static String getServer(URI scmUri) {
		GitURI gitURI = new GitURI(scmUri);
		return gitURI.getRepository().toString();
	}
}

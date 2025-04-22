package org.eclipse.egit.ui.internal.commit;

import java.text.MessageFormat;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.egit.ui.Activator;
import org.eclipse.egit.ui.UIText;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.SharedHeaderFormEditor;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.ide.IDE;

public class CommitEditor extends SharedHeaderFormEditor {

	public static final String ID = "org.eclipse.egit.ui.commitEditor"; //$NON-NLS-1$

	public static final void open(RepositoryCommit commit)
			throws PartInitException {
		CommitEditorInput input = new CommitEditorInput(commit);
		IDE.openEditor(PlatformUI.getWorkbench().getActiveWorkbenchWindow()
				.getActivePage(), input, ID);
	}

	protected void addPages() {
		try {
			addPage(new CommitEditorPage(this));
			addPage(new DiffEditorPage(this));
		} catch (PartInitException e) {
			Activator.error("Error adding page", e); //$NON-NLS-1$
		}
	}

	protected void createHeaderContents(IManagedForm headerForm) {
		RepositoryCommit commit = getCommit();
		ScrolledForm form = headerForm.getForm();
		form.setText(MessageFormat.format(UIText.CommitEditor_TitleHeader,
				commit.getRepositoryName(), commit.abbreviate()));
		form.setToolTipText(commit.getRevCommit().name());
		getToolkit().decorateFormHeading(form.getForm());
	}

	private RepositoryCommit getCommit() {
		return (RepositoryCommit) getAdapter(RepositoryCommit.class);
	}

	public Object getAdapter(Class adapter) {
		if (RepositoryCommit.class == adapter)
			return getEditorInput().getAdapter(adapter);

		return super.getAdapter(adapter);
	}

	public void init(IEditorSite site, IEditorInput input)
			throws PartInitException {
		if (input.getAdapter(RepositoryCommit.class) == null) {
			throw new PartInitException(
					"Input could not be adapted to commit object"); //$NON-NLS-1$
		}
		super.init(site, input);
		setPartName(input.getName());
		setTitleToolTip(input.getToolTipText());
	}

	public void doSave(IProgressMonitor monitor) {
	}

	public void doSaveAs() {
	}

	public boolean isSaveAsAllowed() {
		return false;
	}

}

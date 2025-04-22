package org.eclipse.egit.ui.internal.clone;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.egit.ui.UIText;
import org.eclipse.egit.ui.internal.repository.RepositoriesViewContentProvider;
import org.eclipse.egit.ui.internal.repository.RepositoriesViewLabelProvider;
import org.eclipse.egit.ui.internal.repository.RepositoryTreeNode;
import org.eclipse.egit.ui.internal.repository.RepositoryTreeNode.RepositoryTreeNodeType;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Composite;

public class GitImportWithDirectoriesPage extends GitSelectWizardPage {

	private TreeViewer tv;

	public GitImportWithDirectoriesPage() {
		super();
		setTitle(UIText.GitImportWithDirectoriesPage_PageTitle);
		setMessage(UIText.GitImportWithDirectoriesPage_PageMessage);
	}

	public void setRepository(Repository repo) {
		List<RepositoryTreeNode<Repository>> input = new ArrayList<RepositoryTreeNode<Repository>>();
		if (repo != null)
			input.add(new RepositoryTreeNode<Repository>(null,
					RepositoryTreeNodeType.WORKINGDIR, repo, repo));
		tv.setInput(input);
		tv.setSelection(new StructuredSelection(input.get(0)));
	}

	public void createControl(Composite parent) {
		super.createControl(parent);

		Composite main = (Composite) getControl();

		tv = new TreeViewer(main, SWT.SINGLE | SWT.H_SCROLL | SWT.V_SCROLL
				| SWT.BORDER);
		tv.setContentProvider(new RepositoriesViewContentProvider());
		GridDataFactory.fillDefaults().grab(true, true).applyTo(tv.getTree());
		new RepositoriesViewLabelProvider(tv);

		SelectionListener sl = new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				tv.getTree().setEnabled(!newProjectWizard.getSelection());
			}

		};

		generalWizard.addSelectionListener(sl);
		importExisting.addSelectionListener(sl);
		newProjectWizard.addSelectionListener(sl);

		tv.addSelectionChangedListener(new ISelectionChangedListener() {

			public void selectionChanged(SelectionChangedEvent event) {
				checkPage();
			}
		});

		checkPage();
		setControl(main);
	}

	public String getPath() {
		IStructuredSelection sel = (IStructuredSelection) tv.getSelection();
		RepositoryTreeNode node = (RepositoryTreeNode) sel.getFirstElement();
		if (node != null && node.getType() == RepositoryTreeNodeType.FOLDER)
			return ((File) node.getObject()).getPath();
		if (node != null && node.getType() == RepositoryTreeNodeType.WORKINGDIR)
			return node.getRepository().getWorkDir().getPath();
		return null;
	}

	protected void checkPage() {
		super.checkPage();
		if (getErrorMessage() != null)
			return;

		if (newProjectWizard.getSelection())
			return;

		IStructuredSelection sel = (IStructuredSelection) tv.getSelection();
		try {
			if (sel.isEmpty()) {
				setErrorMessage(UIText.GitImportWithDirectoriesPage_SelectFolderMessage);
				return;
			}
			RepositoryTreeNode node = (RepositoryTreeNode) sel
					.getFirstElement();
			if (node.getType() != RepositoryTreeNodeType.FOLDER
					&& node.getType() != RepositoryTreeNodeType.WORKINGDIR) {
				setErrorMessage(UIText.GitImportWithDirectoriesPage_SelectFolderMessage);
				return;
			}
		} finally {
			setPageComplete(getErrorMessage() == null);
		}
	}

}

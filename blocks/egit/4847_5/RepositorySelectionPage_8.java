package org.eclipse.egit.ui.internal.clone;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.egit.ui.Activator;
import org.eclipse.egit.ui.UIText;
import org.eclipse.egit.ui.internal.clone.GitCloneSourceProviderExtension.CloneSourceProvider;
import org.eclipse.egit.ui.internal.provisional.wizards.RepositoryServerInfo;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.dialogs.FilteredTree;
import org.eclipse.ui.dialogs.PatternFilter;

public class RepositoryLocationPage extends WizardPage {

	private final List<CloneSourceProvider> repositoryImports;

	private Map<CloneSourceProvider, WizardPage> resolvedWizardPages;

	private TreeViewer tv;

	public RepositoryLocationPage(List<CloneSourceProvider> cloneSourceProvider) {
		super(RepositoryLocationPage.class.getName());
		cloneSourceProvider.add(0, CloneSourceProvider.LOCAL);
		this.repositoryImports = cloneSourceProvider;
		resolvedWizardPages = new HashMap<CloneSourceProvider, WizardPage>();
		setTitle(UIText.RepositoryLocationPage_title);
		setMessage(UIText.RepositoryLocationPage_info);
	}

	public void createControl(Composite parent) {
		Composite main = new Composite(parent, SWT.NONE);

		GridLayoutFactory.fillDefaults().numColumns(2).margins(0, 0)
				.applyTo(main);

		GridDataFactory.fillDefaults().grab(true, true).applyTo(main);
		FilteredTree tree = new FilteredTree(main, SWT.SINGLE | SWT.BORDER
				| SWT.H_SCROLL | SWT.V_SCROLL, new PatternFilter(), true);

		tv = tree.getViewer();
		GridDataFactory.fillDefaults().grab(true, true).applyTo(tree);
		tv.setContentProvider(new RepositoryLocationContentProvider());

		tv.setLabelProvider(new RepositoryLocationLabelProvider());

		tv.addSelectionChangedListener(new ISelectionChangedListener() {

			public void selectionChanged(SelectionChangedEvent event) {
				checkPage();
			}
		});

		tv.setInput(repositoryImports);
		setControl(main);
	}

	private void checkPage() {
		setErrorMessage(null);
		boolean complete = false;
		IStructuredSelection selection = (IStructuredSelection) tv
				.getSelection();
		if (selection.size() == 1) {
			Object element = selection.getFirstElement();
			if (element instanceof CloneSourceProvider) {
				CloneSourceProvider repositoryImport = (CloneSourceProvider) element;
				if (repositoryImport.equals(CloneSourceProvider.LOCAL)
						|| repositoryImport.hasFixLocation())
					complete = true;
			} else if (element instanceof RepositoryServerInfo) {
				complete = true;
			}
		}

		setPageComplete(complete);
	}

	@Override
	public IWizardPage getNextPage() {
		IStructuredSelection selection = (IStructuredSelection) tv
				.getSelection();

		if (selection.size() == 1) {
			Object element = selection.getFirstElement();
			if (element instanceof CloneSourceProvider) {
				return getNextPage((CloneSourceProvider) element);
			} else if (element instanceof RepositoryServerInfo) {
				Object parent = ((ITreeContentProvider) tv.getContentProvider())
						.getParent(element);
				if (parent instanceof CloneSourceProvider)
					return getNextPage((CloneSourceProvider) parent);
			}
		}

		return null;

	}

	private IWizardPage getNextPage(CloneSourceProvider repositoryImport) {
		if (repositoryImport.equals(CloneSourceProvider.LOCAL))
			return getWizard().getNextPage(this);
		else
			return getWizardPage(repositoryImport);
	}

	private WizardPage getWizardPage(CloneSourceProvider repositoryImport) {
		WizardPage nextPage;
		nextPage = resolvedWizardPages.get(repositoryImport);
		if (nextPage == null) {
			try {
				nextPage = repositoryImport.getRepositorySearchPage();
			} catch (CoreException e) {
				Activator.error(e.getLocalizedMessage(), e);
			}
			if (nextPage != null) {
				nextPage.setWizard(getWizard());
				resolvedWizardPages.put(repositoryImport, nextPage);
			}
		}
		return nextPage;
	}
}

package org.eclipse.egit.ui.internal.selection;

import java.util.function.Supplier;

import org.eclipse.egit.ui.internal.repository.tree.RepositoryNode;
import org.eclipse.jface.viewers.IPostSelectionProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jgit.lib.Repository;

public class RepositorySelectionProvider extends AbstractSelectionProvider {

	private final ISelectionProvider baseProvider;

	private final Supplier<? extends Repository> repositoryProvider;

	private final ISelectionChangedListener selectionHook = event -> fireSelectionChanged(
			getSelectionListeners());

	private final ISelectionChangedListener postSelectionHook = event -> fireSelectionChanged(
			getPostSelectionListeners());

	public RepositorySelectionProvider(ISelectionProvider baseProvider,
			Supplier<? extends Repository> repositoryProvider) {
		this.repositoryProvider = repositoryProvider;
		this.baseProvider = baseProvider;
		baseProvider.addSelectionChangedListener(selectionHook);
		if (baseProvider instanceof IPostSelectionProvider) {
			((IPostSelectionProvider) baseProvider)
					.addPostSelectionChangedListener(postSelectionHook);
		}
	}

	@Override
	public ISelection getSelection() {
		ISelection selection = baseProvider.getSelection();
		if (selection.isEmpty() && selection instanceof IStructuredSelection) {
			Repository repository = repositoryProvider.get();
			if (repository != null) {
				return new StructuredSelection(
						new RepositoryNode(null, repository));
			}
		}
		return selection;
	}

	@Override
	public void setSelection(ISelection selection) {
		baseProvider.setSelection(selection);
	}
}

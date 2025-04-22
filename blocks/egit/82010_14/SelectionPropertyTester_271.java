package org.eclipse.egit.ui.internal.selection;

import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.Map;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.ui.AbstractSourceProvider;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.ISources;
import org.eclipse.ui.IWindowListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.services.IServiceLocator;

public class RepositorySourceProvider extends AbstractSourceProvider
		implements ISelectionListener, IWindowListener {

	public static final String REPOSITORY_PROPERTY = "org.eclipse.egit.ui.currentRepository"; //$NON-NLS-1$

	private WeakReference<Repository> repositoryRef;

	@Override
	public void initialize(IServiceLocator locator) {
		super.initialize(locator);
		PlatformUI.getWorkbench().addWindowListener(this);
	}

	@Override
	public void dispose() {
		PlatformUI.getWorkbench().removeWindowListener(this);
		repositoryRef = null;
	}

	@Override
	public Map getCurrentState() {
		@SuppressWarnings("resource")
		Repository repository = repositoryRef == null ? null
				: repositoryRef.get();
		if (repository == null) {
			return Collections.singletonMap(REPOSITORY_PROPERTY, ""); //$NON-NLS-1$
		}
		return Collections.singletonMap(REPOSITORY_PROPERTY,
				repository.getDirectory().getAbsolutePath());
	}

	@Override
	public String[] getProvidedSourceNames() {
		return new String[] { REPOSITORY_PROPERTY };
	}

	@Override
	public void selectionChanged(IWorkbenchPart part, ISelection selection) {
		Repository newRepository;
		if (selection == null) {
			newRepository = null;
		} else {
			newRepository = SelectionUtils.getRepository(
					SelectionUtils.getStructuredSelection(selection));
		}
		@SuppressWarnings("resource")
		Repository currentRepository = repositoryRef == null ? null
				: repositoryRef.get();
		if (currentRepository == null && repositoryRef != null) {
			repositoryRef = null;
			if (newRepository == null) {
				fireSourceChanged(ISources.ACTIVE_WORKBENCH_WINDOW,
						REPOSITORY_PROPERTY, ""); //$NON-NLS-1$
				return;
			}
		}
		if (currentRepository != newRepository) {
			if (newRepository != null) {
				repositoryRef = new WeakReference<>(newRepository);
				fireSourceChanged(ISources.ACTIVE_WORKBENCH_WINDOW,
						REPOSITORY_PROPERTY,
						newRepository.getDirectory().getAbsolutePath());
			} else {
				repositoryRef = null;
				fireSourceChanged(ISources.ACTIVE_WORKBENCH_WINDOW,
						REPOSITORY_PROPERTY, ""); //$NON-NLS-1$
			}
		}
	}

	@Override
	public void windowActivated(IWorkbenchWindow window) {
		window.getSelectionService().addSelectionListener(this);
	}

	@Override
	public void windowDeactivated(IWorkbenchWindow window) {
		window.getSelectionService().removeSelectionListener(this);
	}

	@Override
	public void windowClosed(IWorkbenchWindow window) {
	}

	@Override
	public void windowOpened(IWorkbenchWindow window) {
	}

}

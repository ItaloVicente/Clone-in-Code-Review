package org.eclipse.egit.ui.internal.actions;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.egit.core.project.RepositoryMapping;
import org.eclipse.egit.ui.UIText;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.ISelectionService;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;

public abstract class RepositoryActionHandler extends AbstractHandler {

	protected IProject[] getProjectsForSelectedResources(ExecutionEvent event)
			throws ExecutionException {
		Set<IProject> ret = new HashSet<IProject>();
		for (IResource resource : (IResource[]) getSelectedAdaptables(
				getSelection(event), IResource.class))
			ret.add(resource.getProject());
		return ret.toArray(new IProject[ret.size()]);
	}

	protected Repository[] getRepositoriesFor(final IProject[] projects) {
		Set<Repository> ret = new HashSet<Repository>();
		for (IProject project : projects) {
			RepositoryMapping repositoryMapping = RepositoryMapping
					.getMapping(project);
			if (repositoryMapping == null)
				return new Repository[0];
			ret.add(repositoryMapping.getRepository());
		}
		return ret.toArray(new Repository[ret.size()]);
	}

	protected IProject[] getProjectsInRepositoryOfSelectedResources(
			ExecutionEvent event) throws ExecutionException {
		Set<IProject> ret = new HashSet<IProject>();
		Repository[] repositories = getRepositoriesFor(getProjectsForSelectedResources(event));
		final IProject[] projects = ResourcesPlugin.getWorkspace().getRoot()
				.getProjects();
		for (IProject project : projects) {
			RepositoryMapping mapping = RepositoryMapping.getMapping(project);
			for (Repository repository : repositories) {
				if (mapping != null && mapping.getRepository() == repository) {
					ret.add(project);
					break;
				}
			}
		}
		return ret.toArray(new IProject[ret.size()]);
	}

	protected Repository getRepository(boolean warn, ExecutionEvent event)
			throws ExecutionException {
		RepositoryMapping mapping = null;
		for (IProject project : getSelectedProjects(event)) {
			RepositoryMapping repositoryMapping = RepositoryMapping
					.getMapping(project);
			if (mapping == null)
				mapping = repositoryMapping;
			if (repositoryMapping == null)
				return null;
			if (mapping.getRepository() != repositoryMapping.getRepository()) {
				if (warn)
					MessageDialog.openError(getShell(event),
							UIText.RepositoryAction_multiRepoSelectionTitle,
							UIText.RepositoryAction_multiRepoSelection);
				return null;
			}
		}
		if (mapping == null) {
			if (warn)
				MessageDialog.openError(getShell(event),
						UIText.RepositoryAction_errorFindingRepoTitle,
						UIText.RepositoryAction_errorFindingRepo);
			return null;
		}

		final Repository repository = mapping.getRepository();
		return repository;
	}

	protected Repository[] getRepositories(ExecutionEvent event)
			throws ExecutionException {
		IProject[] selectedProjects = getSelectedProjects(event);
		Set<Repository> repos = new HashSet<Repository>(selectedProjects.length);
		for (IProject project : selectedProjects) {
			RepositoryMapping repositoryMapping = RepositoryMapping
					.getMapping(project);
			if (repositoryMapping == null)
				return new Repository[0];
			repos.add(repositoryMapping.getRepository());
		}
		return repos.toArray(new Repository[repos.size()]);
	}

	protected IStructuredSelection getSelection(ExecutionEvent event)
			throws ExecutionException {
		ISelection selection;
		if (event != null)
			selection = HandlerUtil.getCurrentSelectionChecked(event);
		else {
			ISelectionService srv = (ISelectionService) PlatformUI
					.getWorkbench().getActiveWorkbenchWindow().getService(
							ISelectionService.class);
			if (srv == null)
				throw new ExecutionException(
						UIText.RepositoryActionHandler_CouldNotGetSelection_message);
			else
				selection = srv.getSelection();
		}
		if (selection instanceof IStructuredSelection)
			return (IStructuredSelection) selection;
		return new StructuredSelection();
	}

	@SuppressWarnings("unchecked")
	protected Object[] getSelectedAdaptables(ISelection selection, Class c) {
		ArrayList result = null;
		if (selection != null && !selection.isEmpty()) {
			result = new ArrayList();
			Iterator elements = ((IStructuredSelection) selection).iterator();
			while (elements.hasNext()) {
				Object adapter = getAdapter(elements.next(), c);
				if (c.isInstance(adapter)) {
					result.add(adapter);
				}
			}
		}
		if (result != null && !result.isEmpty()) {
			return result.toArray((Object[]) Array
					.newInstance(c, result.size()));
		}
		return (Object[]) Array.newInstance(c, 0);
	}

	private Object getAdapter(Object adaptable, Class c) {
		if (c.isInstance(adaptable)) {
			return adaptable;
		}
		if (adaptable instanceof IAdaptable) {
			IAdaptable a = (IAdaptable) adaptable;
			Object adapter = a.getAdapter(c);
			if (c.isInstance(adapter)) {
				return adapter;
			}
		}
		return null;
	}

	private IProject[] getSelectedProjects(ExecutionEvent event)
			throws ExecutionException {
		IResource[] selectedResources = getSelectedResources(event);
		if (selectedResources.length == 0)
			return new IProject[0];
		ArrayList<IProject> projects = new ArrayList<IProject>();
		for (int i = 0; i < selectedResources.length; i++) {
			IResource resource = selectedResources[i];
			if (resource.getType() == IResource.PROJECT) {
				projects.add((IProject) resource);
			}
		}
		return projects.toArray(new IProject[projects.size()]);
	}

	protected IResource[] getSelectedResources(ExecutionEvent event)
			throws ExecutionException {
		Set<IResource> result = new HashSet<IResource>();
		for (Object o : getSelection(event).toList())
			if (o instanceof IResource)
				result.add((IResource) o);
		return result.toArray(new IResource[result.size()]);
	}

	protected Shell getShell(ExecutionEvent event) throws ExecutionException {
		return HandlerUtil.getActiveShellChecked(event);
	}

	protected IWorkbenchPage getPartPage(ExecutionEvent event)
			throws ExecutionException {
		return HandlerUtil.getActivePartChecked(event).getSite().getPage();
	}
}

package org.eclipse.egit.ui.internal.selection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.expressions.IEvaluationContext;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.mapping.ResourceMapping;
import org.eclipse.core.resources.mapping.ResourceTraversal;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.egit.core.AdapterUtils;
import org.eclipse.egit.core.internal.util.ResourceUtil;
import org.eclipse.egit.ui.Activator;
import org.eclipse.egit.ui.internal.CommonUtils;
import org.eclipse.egit.ui.internal.UIText;
import org.eclipse.egit.ui.internal.revision.FileRevisionEditorInput;
import org.eclipse.egit.ui.internal.trace.GitTraceLocation;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jgit.annotations.NonNull;
import org.eclipse.jgit.annotations.Nullable;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.team.core.history.IFileRevision;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.ISources;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.IHandlerService;
import org.eclipse.ui.part.MultiPageEditorPart;

public class SelectionUtils {

	@Nullable
	public static Repository getRepository(
			@NonNull IStructuredSelection selection) {
		return getRepository(false, selection, null);
	}

	@Nullable
	public static Repository getRepository(
			@Nullable IEvaluationContext evaluationContext) {
		return getRepository(false, getSelection(evaluationContext), null);
	}

	@Nullable
	public static Repository getRepositoryOrWarn(
			@NonNull IStructuredSelection selection, @NonNull Shell shell) {
		return getRepository(true, selection, shell);
	}

	@NonNull
	public static IStructuredSelection getSelection(
			@Nullable IEvaluationContext context) {
		if (context == null)
			return StructuredSelection.EMPTY;

		Object selection = context
				.getVariable(ISources.ACTIVE_MENU_SELECTION_NAME);
		if (!(selection instanceof ISelection))
			selection = context
					.getVariable(ISources.ACTIVE_CURRENT_SELECTION_NAME);

		if (selection instanceof ITextSelection)
			return getSelectionFromEditorInput(context);
		else if (selection instanceof IStructuredSelection)
			return (IStructuredSelection) selection;
		return StructuredSelection.EMPTY;
	}

	@NonNull
	public static IStructuredSelection getStructuredSelection(
			@NonNull ISelection selection) {
		if (selection instanceof ITextSelection)
			return getSelectionFromEditorInput(getEvaluationContext());
		else if (selection instanceof IStructuredSelection)
			return (IStructuredSelection) selection;
		return StructuredSelection.EMPTY;
	}

	@NonNull
	public static IPath[] getSelectedLocations(
			@NonNull IStructuredSelection selection) {
		Set<IPath> result = new LinkedHashSet<>();
		for (Object o : selection.toList()) {
			IResource resource = AdapterUtils.adaptToAnyResource(o);
			if (resource != null) {
				IPath location = resource.getLocation();
				if (location != null)
					result.add(location);
			} else {
				IPath location = AdapterUtils.adapt(o, IPath.class);
				if (location != null)
					result.add(location);
				else
					for (IResource r : extractResourcesFromMapping(o)) {
						IPath l = r.getLocation();
						if (l != null)
							result.add(l);
					}
			}
		}
		return result.toArray(new IPath[result.size()]);
	}

	@NonNull
	public static IResource[] getSelectedResources(
			@NonNull IStructuredSelection selection) {
		Set<IResource> result = new LinkedHashSet<>();
		for (Object o : selection.toList()) {
			IResource resource = AdapterUtils.adaptToAnyResource(o);
			if (resource != null)
				result.add(resource);
			else
				result.addAll(extractResourcesFromMapping(o));
		}
		return result.toArray(new IResource[result.size()]);
	}

	private static List<IResource> extractResourcesFromMapping(Object o) {
		ResourceMapping mapping = AdapterUtils.adapt(o, ResourceMapping.class);
		if (mapping == null)
			return Collections.emptyList();

		ResourceTraversal[] traversals;
		try {
			traversals = mapping.getTraversals(null, null);
		} catch (CoreException e) {
			Activator.logError(e.getMessage(), e);
			return Collections.emptyList();
		}

		if (traversals.length == 0)
			return Collections.emptyList();

		List<IResource> result = new ArrayList<>();
		for (ResourceTraversal traversal : traversals) {
			IResource[] resources = traversal.getResources();
			result.addAll(Arrays.asList(resources));
		}
		return result;
	}

	@NonNull
	private static Set<Object> getSelectionContents(
			@NonNull IStructuredSelection selection) {
		Set<Object> result = new HashSet<>();
		for (Object o : selection.toList()) {
			Repository r = AdapterUtils.adapt(o, Repository.class);
			if (r != null) {
				result.add(r);
				continue;
			}
			IResource resource = AdapterUtils.adaptToAnyResource(o);
			if (resource != null) {
				result.add(resource);
				continue;
			}
			ResourceMapping mapping = AdapterUtils.adapt(o,
					ResourceMapping.class);
			if (mapping != null) {
				result.addAll(extractResourcesFromMapping(mapping));
			} else {
				IPath location = AdapterUtils.adapt(o, IPath.class);
				if (location != null) {
					result.add(location);
				}
			}
		}
		return result;
	}

	@Nullable
	private static Repository getRepository(boolean warn,
			@NonNull IStructuredSelection selection, Shell shell) {
		if (selection.isEmpty()) {
			return null;
		}
		Set<Object> elements = getSelectionContents(selection);
		if (GitTraceLocation.SELECTION.isActive())
			GitTraceLocation.getTrace().trace(
					GitTraceLocation.SELECTION.getLocation(), "selection=" //$NON-NLS-1$
							+ selection + ", elements=" + elements.toString()); //$NON-NLS-1$

		boolean hadNull = false;
		Repository result = null;
		for (Object location : elements) {
			Repository repo = null;
			if (location instanceof Repository) {
				repo = (Repository) location;
			} else if (location instanceof IResource) {
				repo = ResourceUtil.getRepository((IResource) location);
			} else if (location instanceof IPath) {
				repo = ResourceUtil.getRepository((IPath) location);
			}
			if (repo == null) {
				hadNull = true;
			}
			if (result == null) {
				result = repo;
			}
			boolean mismatch = hadNull && result != null;
			if (mismatch || result != repo) {
				if (warn) {
					MessageDialog.openError(shell,
							UIText.RepositoryAction_multiRepoSelectionTitle,
							UIText.RepositoryAction_multiRepoSelection);
				}
				return null;
			}
		}

		if (result == null) {
			for (Object o : selection.toArray()) {
				Repository nextRepo = AdapterUtils.adapt(o, Repository.class);
				if (nextRepo != null && result != null && result != nextRepo) {
					if (warn)
						MessageDialog
								.openError(
										shell,
										UIText.RepositoryAction_multiRepoSelectionTitle,
										UIText.RepositoryAction_multiRepoSelection);
					return null;
				}
				result = nextRepo;
			}
		}

		if (result == null) {
			if (warn)
				MessageDialog.openError(shell,
						UIText.RepositoryAction_errorFindingRepoTitle,
						UIText.RepositoryAction_errorFindingRepo);
			return null;
		}

		return result;
	}

	private static IStructuredSelection getSelectionFromEditorInput(
			IEvaluationContext context) {
		Object part = context.getVariable(ISources.ACTIVE_PART_NAME);
		if (!(part instanceof IEditorPart)) {
			return StructuredSelection.EMPTY;
		}
		Object object = context.getVariable(ISources.ACTIVE_EDITOR_INPUT_NAME);
		Object editor = context.getVariable(ISources.ACTIVE_EDITOR_NAME);
		if (editor instanceof MultiPageEditorPart) {
			Object nestedEditor = ((MultiPageEditorPart) editor)
					.getSelectedPage();
			if (nestedEditor instanceof IEditorPart) {
				object = ((IEditorPart) nestedEditor).getEditorInput();
			}
		}
		if (!(object instanceof IEditorInput)
				&& (editor instanceof IEditorPart)) {
			object = ((IEditorPart) editor).getEditorInput();
		}
		if (object instanceof IEditorInput) {
			IEditorInput editorInput = (IEditorInput) object;
			IResource resource = org.eclipse.ui.ide.ResourceUtil
					.getResource(editorInput);
			if (resource != null)
				return new StructuredSelection(resource);
			if (editorInput instanceof FileRevisionEditorInput) {
				FileRevisionEditorInput fileRevisionEditorInput = (FileRevisionEditorInput) editorInput;
				IFileRevision fileRevision = fileRevisionEditorInput
						.getFileRevision();
				if (fileRevision != null)
					return new StructuredSelection(fileRevision);
			}
		}

		return StructuredSelection.EMPTY;
	}

	private static IEvaluationContext getEvaluationContext() {
		IEvaluationContext ctx;
		IWorkbenchWindow activeWorkbenchWindow = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow();
		if (activeWorkbenchWindow == null)
			return null;
		IHandlerService hsr = CommonUtils.getService(activeWorkbenchWindow, IHandlerService.class);
		ctx = hsr.getCurrentState();
		return ctx;
	}

}

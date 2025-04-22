package org.eclipse.egit.ui.internal.history.command;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.compare.CompareEditorInput;
import org.eclipse.compare.CompareUI;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.preferences.DefaultScope;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.egit.core.project.RepositoryMapping;
import org.eclipse.egit.ui.UIText;
import org.eclipse.egit.ui.internal.GitCompareFileRevisionEditorInput;
import org.eclipse.jface.util.OpenStrategy;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.Tag;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.team.ui.history.IHistoryView;
import org.eclipse.team.ui.synchronize.SaveableCompareEditorInput;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IReusableEditor;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;

abstract class AbstractHistoryCommanndHandler extends AbstractHandler {

	private static final String TEAM_UI_PLUGIN = "org.eclipse.team.ui"; //$NON-NLS-1$

	private static final String REUSE_COMPARE_EDITOR_PREFID = "org.eclipse.team.ui.reuse_open_compare_editors"; //$NON-NLS-1$

	protected IWorkbenchPart getPart(ExecutionEvent event)
			throws ExecutionException {
		return HandlerUtil.getActivePartChecked(event);
	}

	protected IStructuredSelection getSelection(ExecutionEvent event)
			throws ExecutionException {
		ISelection selection;
		if (event != null)
			selection = HandlerUtil.getCurrentSelectionChecked(event);
		else
			selection = PlatformUI.getWorkbench().getActiveWorkbenchWindow()
					.getActivePage().getSelection();
		if (selection instanceof IStructuredSelection) {
			return (IStructuredSelection) selection;
		}
		return new StructuredSelection();
	}

	protected Object getInput(ExecutionEvent event) throws ExecutionException {
		IWorkbenchPart part;
		if (event != null)
			part = getPart(event);
		else
			part = PlatformUI.getWorkbench().getActiveWorkbenchWindow()
					.getActivePage().getActivePart();

		if (!(part instanceof IHistoryView))
			throw new ExecutionException(
					UIText.AbstractHistoryCommanndHandler_NoInputMessage);
		return (((IHistoryView) part).getHistoryPage().getInput());
	}

	protected void openInCompare(ExecutionEvent event, CompareEditorInput input)
			throws ExecutionException {
		IWorkbenchPage workBenchPage = HandlerUtil
				.getActiveWorkbenchWindowChecked(event).getActivePage();
		IEditorPart editor = findReusableCompareEditor(input, workBenchPage);
		if (editor != null) {
			IEditorInput otherInput = editor.getEditorInput();
			if (otherInput.equals(input)) {
				if (OpenStrategy.activateOnOpen())
					workBenchPage.activate(editor);
				else
					workBenchPage.bringToTop(editor);
			} else {
				CompareUI.reuseCompareEditor(input, (IReusableEditor) editor);
				if (OpenStrategy.activateOnOpen())
					workBenchPage.activate(editor);
				else
					workBenchPage.bringToTop(editor);
			}
		} else {
			CompareUI.openCompareEditor(input);
		}
	}

	private IEditorPart findReusableCompareEditor(CompareEditorInput input,
			IWorkbenchPage page) {
		IEditorReference[] editorRefs = page.getEditorReferences();
		for (int i = 0; i < editorRefs.length; i++) {
			IEditorPart part = editorRefs[i].getEditor(false);
			if (part != null
					&& (part.getEditorInput() instanceof GitCompareFileRevisionEditorInput)
					&& part instanceof IReusableEditor
					&& part.getEditorInput().equals(input)) {
				return part;
			}
		}
		if (isReuseOpenEditor()) {
			for (int i = 0; i < editorRefs.length; i++) {
				IEditorPart part = editorRefs[i].getEditor(false);
				if (part != null
						&& (part.getEditorInput() instanceof SaveableCompareEditorInput)
						&& part instanceof IReusableEditor && !part.isDirty()) {
					return part;
				}
			}
		}
		return null;
	}

	private boolean isReuseOpenEditor() {
		boolean defaultReuse = new DefaultScope().getNode(TEAM_UI_PLUGIN)
				.getBoolean(REUSE_COMPARE_EDITOR_PREFID, false);
		return new InstanceScope().getNode(TEAM_UI_PLUGIN).getBoolean(
				REUSE_COMPARE_EDITOR_PREFID, defaultReuse);
	}

	protected Repository getRepository(ExecutionEvent event)
			throws ExecutionException {
		Object input = getInput(event);
		return RepositoryMapping.getMapping((IResource) input).getRepository();
	}

	protected List<Tag> getRevTags(ExecutionEvent event)
			throws ExecutionException {
		Repository repo = getRepository(event);
		Collection<Ref> revTags = repo.getTags().values();
		List<Tag> tags = new ArrayList<Tag>();
		RevWalk walk = new RevWalk(repo);
		for (Ref ref : revTags) {
			try {
				Tag tag = walk.parseTag(repo.resolve(ref.getName()))
						.asTag(walk);
				tags.add(tag);
			} catch (IOException e) {
				throw new ExecutionException(e.getMessage(), e);
			}
		}
		return tags;
	}
}

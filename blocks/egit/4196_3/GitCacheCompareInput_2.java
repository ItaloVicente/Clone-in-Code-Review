package org.eclipse.egit.ui.internal.synchronize.action;

import static org.eclipse.ui.PlatformUI.getWorkbench;

import java.io.IOException;
import java.util.Iterator;

import org.eclipse.compare.CompareUI;
import org.eclipse.compare.ITypedElement;
import org.eclipse.core.resources.IFile;
import org.eclipse.egit.ui.internal.CompareUtils;
import org.eclipse.egit.ui.internal.GitCompareFileRevisionEditorInput;
import org.eclipse.egit.ui.internal.synchronize.model.GitModelBlob;
import org.eclipse.egit.ui.internal.synchronize.model.GitModelWorkingFile;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.team.ui.synchronize.ISynchronizePageConfiguration;
import org.eclipse.team.ui.synchronize.ISynchronizePageSite;
import org.eclipse.team.ui.synchronize.SaveableCompareEditorInput;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;

public class GitOpenInCompareAction extends Action {

	private final Action oldAction;

	private final ISynchronizePageConfiguration conf;

	public GitOpenInCompareAction(ISynchronizePageConfiguration configuration,
			Action oldAction) {
		this.conf = configuration;
		this.oldAction = oldAction;
	}

	public void run() {
		ISelection selection = conf.getSite().getSelectionProvider()
				.getSelection();
		if (selection instanceof IStructuredSelection) {
			IStructuredSelection sel = ((IStructuredSelection) selection);
			for (Iterator iterator = sel.iterator(); iterator.hasNext();) {
				Object obj = iterator.next();
				if (obj instanceof GitModelBlob)
					handleGitObjectComparison((GitModelBlob) obj);
				else {
					oldAction.run();
					break;
				}
			}
		}
	}

	private void handleGitObjectComparison(GitModelBlob obj) {
		ITypedElement left;
		ITypedElement right;
		if (obj instanceof GitModelWorkingFile) {
			IFile file = (IFile) obj.getResource();
			left= SaveableCompareEditorInput.createFileElement(file);
			right = getCachedFileElement(file);
		} else {
			oldAction.run();
			return;
		}

		GitCompareFileRevisionEditorInput in = new GitCompareFileRevisionEditorInput(
				left, right, null);

		IWorkbenchPage page = getWorkbenchPage(conf.getSite());
		CompareUI.openCompareEditorOnPage(in, page);
	}

	private static IWorkbenchPage getWorkbenchPage(ISynchronizePageSite site) {
		IWorkbenchPage page = null;
		if (site == null || site.getWorkbenchSite() == null) {
			IWorkbenchWindow window = getWorkbench().getActiveWorkbenchWindow();
			if (window != null)
				page = window.getActivePage();
		} else
			page = site.getWorkbenchSite().getPage();

		return page;
	}

	private ITypedElement getCachedFileElement(IFile file) {
		try {
			return CompareUtils.getHeadTypedElement(file);
		} catch (IOException e) {
			return null;
		}
	}

}

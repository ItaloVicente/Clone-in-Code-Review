package org.eclipse.egit.ui.internal.commit.command;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;
import org.eclipse.egit.core.internal.IRepositoryCommit;
import org.eclipse.egit.ui.internal.UIText;
import org.eclipse.egit.ui.internal.commit.DiffEditor;
import org.eclipse.egit.ui.internal.commit.DiffEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.progress.UIJob;

public class UnifiedDiffHandler extends CommitCommandHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		List<IRepositoryCommit> commits = getCommits(event);
		if (commits.size() == 2) {
			Collections.sort(commits,
					Comparator.<IRepositoryCommit> comparingInt(
							repoCommit -> repoCommit.getRevCommit()
									.getCommitTime())
							.reversed());
			show(commits.get(0), commits.get(1));
		}
		return null;
	}

	public static void show(IRepositoryCommit tip, IRepositoryCommit base) {
		Assert.isNotNull(tip);
		DiffEditorInput input = new DiffEditorInput(tip, base);
		IWorkbenchWindow window = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow();
		IWorkbenchPage page = window.getActivePage();
		IEditorReference[] editors = page.findEditors(input,
				DiffEditor.EDITOR_ID,
				IWorkbenchPage.MATCH_ID + IWorkbenchPage.MATCH_INPUT);
		if (editors != null && editors.length > 0) {
			IEditorPart existing = editors[0].getEditor(false);
			if (existing != null) {
				page.activate(existing);
				return;
			}
		}
		DiffEditor.DiffJob job = DiffEditor.getDiffer(tip, base);
		job.addJobChangeListener(new JobChangeAdapter() {
			@Override
			public void done(IJobChangeEvent evt) {
				if (!evt.getResult().isOK()) {
					return;
				}
				input.setDocument(job.getDocument());
				new UIJob(UIText.DiffEditor_TaskUpdatingViewer) {

					@Override
					public IStatus runInUIThread(IProgressMonitor uiMonitor) {
						try {
							page.openEditor(input, DiffEditor.EDITOR_ID, true);
						} catch (PartInitException e) {
							return e.getStatus();
						}
						return Status.OK_STATUS;
					}
				}.schedule(50);
			}
		});
		job.setUser(true);
		job.schedule();
	}
}

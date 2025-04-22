package org.eclipse.egit.ui.internal.commit;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.ui.OpenAndLinkWithEditorHelper;

public class RepositoryCommitOpener
		extends OpenAndLinkWithEditorHelper {

	@SuppressWarnings("unused")
	public static void setup(StructuredViewer viewer) {
		new RepositoryCommitOpener(viewer);
	}

	private RepositoryCommitOpener(StructuredViewer viewer) {
		super(viewer);
	}

	@Override
	protected void open(ISelection selection, boolean activate) {
		handleOpen(selection, activate);
	}

	@Override
	protected void activate(ISelection selection) {
		handleOpen(selection, true);
	}

	private void handleOpen(ISelection selection, boolean activateOnOpen) {
		if (selection instanceof IStructuredSelection) {
			for (Object element : (IStructuredSelection) selection) {
				if (element instanceof RepositoryCommit) {
					CommitEditor.openQuiet((RepositoryCommit) element,
							activateOnOpen);
				}
			}
		}
	}
}

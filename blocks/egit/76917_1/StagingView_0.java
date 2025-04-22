package org.eclipse.egit.internal.mylyn.ui.commit;

import org.eclipse.egit.ui.Activator;
import org.eclipse.egit.ui.internal.staging.StagingView;
import org.eclipse.mylyn.tasks.core.ITask;
import org.eclipse.mylyn.tasks.core.TaskActivationAdapter;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

public class CommitTextTaskActivationListener extends TaskActivationAdapter {

	@Override
	public void taskActivated(ITask task) {
		try {
			StagingView view = (StagingView) PlatformUI.getWorkbench()
					.getActiveWorkbenchWindow().getActivePage()
					.showView(StagingView.VIEW_ID);
			view.updateCommitMessageComponentCommitText();
		} catch (PartInitException e) {
			Activator.logError(e.getMessage(), e);
		}
	}

}

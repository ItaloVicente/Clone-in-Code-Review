package org.eclipse.ui.internal.views.log;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.action.Action;
import org.eclipse.ui.*;
import org.eclipse.ui.ide.IDE;

public class OpenIDELogFileAction extends Action {

	private LogView fView;

	public OpenIDELogFileAction(LogView logView) {
		fView = logView;
	}

	@Override
	public void run() {
		IPath logPath = new Path(fView.getLogFile().getAbsolutePath());
		IFileStore fileStore = EFS.getLocalFileSystem().getStore(logPath);
		if (!fileStore.fetchInfo().isDirectory() && fileStore.fetchInfo().exists()) {
			IWorkbenchWindow ww = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
			IWorkbenchPage page = ww.getActivePage();
			try {
				IDE.openEditorOnFileStore(page, fileStore);
			} catch (PartInitException e) { // do nothing
			}
		}
	}

}

package org.eclipse.egit.ui.internal.repository;

import java.io.File;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.egit.ui.Activator;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.RepositoryCache.FileKey;
import org.eclipse.jgit.util.FS;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.FileTransfer;
import org.eclipse.swt.dnd.TransferData;
import org.eclipse.ui.navigator.CommonDropAdapter;
import org.eclipse.ui.navigator.CommonDropAdapterAssistant;

public class DropAdapterAssistant extends CommonDropAdapterAssistant {
	public DropAdapterAssistant() {
	}

	@Override
	public IStatus handleDrop(CommonDropAdapter aDropAdapter,
			DropTargetEvent aDropTargetEvent, Object aTarget) {
		String[] data = (String[]) aDropTargetEvent.data;
		for (String folder : data) {
			File repoFile = new File(folder);
			if (FileKey.isGitRepository(repoFile, FS.DETECTED))
				Activator.getDefault().getRepositoryUtil()
						.addConfiguredRepository(repoFile);
			else if (!repoFile.getName().equals(Constants.DOT_GIT)) {
				File dotgitfile = new File(repoFile, Constants.DOT_GIT);
				if (FileKey.isGitRepository(dotgitfile, FS.DETECTED))
					Activator.getDefault().getRepositoryUtil()
							.addConfiguredRepository(dotgitfile);
			}
		}
		return Status.OK_STATUS;
	}

	@Override
	public IStatus validateDrop(Object target, int operation,
			TransferData transferData) {
		String[] folders = (String[]) FileTransfer.getInstance().nativeToJava(
				transferData);
		for (String folder : folders) {
			File repoFile = new File(folder);
			if (FileKey.isGitRepository(repoFile, FS.DETECTED)) {
				continue;
			}
			if (!repoFile.getName().equals(Constants.DOT_GIT)) {
				File dotgitfile = new File(repoFile, Constants.DOT_GIT);
				if (FileKey.isGitRepository(dotgitfile, FS.DETECTED))
					continue;
			}
			return Status.CANCEL_STATUS;
		}
		return Status.OK_STATUS;
	}

	@Override
	public boolean isSupportedType(TransferData aTransferType) {
		return FileTransfer.getInstance().isSupportedType(aTransferType);
	}
}

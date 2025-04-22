package org.eclipse.egit.ui.internal.actions;

import java.io.IOException;
import java.util.ArrayList;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.egit.core.CoreText;
import org.eclipse.egit.core.project.RepositoryMapping;
import org.eclipse.egit.ui.UIText;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.GitIndex.Entry;

public class DiscardChangesAction extends RepositoryAction{

	@Override
	public void run(IAction action) {

		boolean performAction = MessageDialog.openConfirm(getShell(),
				UIText.DiscardChangesAction_confirmActionTitle,
				UIText.DiscardChangesAction_confirmActionMessage);
		if (performAction) {
			performDiscardChanges();
		}
	}

	private void performDiscardChanges() {
		ArrayList<IResource> allFiles = new ArrayList<IResource>();

		for (IResource res : getSelectedResources()) {
			allFiles.addAll(getAllMembers(res));
		}

		for (IResource res : allFiles) {
			try {
				discardChange(res);

			} catch (IOException e1) {
				MessageDialog.openError(getShell(),
						UIText.DiscardChangesAction_checkoutErrorTitle,
						UIText.DiscardChangesAction_checkoutErrorMessage);
			} catch (CoreException e) {
				MessageDialog.openError(getShell(),
						UIText.DiscardChangesAction_refreshErrorTitle,
						UIText.DiscardChangesAction_refreshErrorMessage);
			}
		}

		try {
			getRepository(true).getIndex().write();
		} catch (IOException e) {
			MessageDialog.openError(getShell(),
					CoreText.UpdateOperation_failed,
					CoreText.UpdateOperation_failed);
		}
	}

	private void discardChange(IResource res) throws IOException, CoreException {
		Repository repository = getRepository(true);
		String resRelPath = RepositoryMapping.getMapping(res).getRepoRelativePath(res);
		Entry e = repository.getIndex().getEntry(resRelPath);

		if (e != null && e.isModified(repository.getWorkDir())) {
			repository.getIndex().checkoutEntry(repository.getWorkDir(), e);
			res.refreshLocal(0, new NullProgressMonitor());
		}
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	private ArrayList<IResource> getAllMembers(IResource res){
		ArrayList<IResource> ret = new ArrayList<IResource>();
		if (res.getLocation().toFile().isFile()){
			ret.add(res);
		}else{
			getAllMembersHelper(res, ret);
		}
		return ret;
	}


	private void getAllMembersHelper(IResource res, ArrayList<IResource> ret) {
		if (res instanceof IContainer) {
			IContainer cont = (IContainer) res;
			try {
				for (IResource r : cont.members()) {
					if (r.getLocation().toFile().isFile()) {
						ret.add(r);
					} else {
						getAllMembersHelper(r, ret);
					}
				}
			} catch (CoreException e) {
			}
		}
	}

}

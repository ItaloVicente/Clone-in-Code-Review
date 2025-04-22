package org.eclipse.egit.ui.internal.actions;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.egit.core.op.CleanOperation;
import org.eclipse.egit.ui.JobFamilies;
import org.eclipse.egit.ui.UIText;
import org.eclipse.egit.ui.internal.job.JobUtil;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.ui.dialogs.ListSelectionDialog;
import org.eclipse.egit.ui.Activator;

public class CleanActionHandler extends RepositoryActionHandler {
	private Repository repo;

	private Set<String> fileListReturned;

	public Object execute(ExecutionEvent event) throws ExecutionException {
		IResource[] resources = getSelectedResources(event);
		repo = getRepository(true, event);
		fileListReturned = new HashSet<String>();

		if (repo == null)
			return null;
		if (resources.length == 0)
			return null;

		CleanOperation op = new CleanOperation(resources);
		Set<String> fileList = op.dryRun();

		ListSelectionDialog dialog = new ListSelectionDialog(
												getShell(event), // shell
												fileList, // set of file names to populate
												new ArrayContentProvider(), // acp
												new ItemLabelProvider(), // ilp
												UIText.CleanDialog_HeaderMessage // branch name
									);
		dialog.setTitle(UIText.CleanDialog_TitleMessage);

		if (dialog.open() != IDialogConstants.OK_ID)
			return null;

		for (int i = 0; i < dialog.getResult().length; i++)
			fileListReturned.add(dialog.getResult()[i].toString());

		JobUtil.scheduleUserJob(op.setPaths(fileListReturned), "Clean", //$NON-NLS-1$
				JobFamilies.CLEAN);

		try {
			ResourcesPlugin.getWorkspace().getRoot().refreshLocal(
					IResource.DEPTH_INFINITE, null);
		} catch (CoreException e) {
			Activator.getDefault().getLog().log(
					new org.eclipse.core.runtime.Status(
							IStatus.INFO, Activator.getPluginId(), IStatus.ERROR, e.getMessage(), e
					)
			);
		}

		return null;
	}

	class ItemLabelProvider implements ILabelProvider {
		public org.eclipse.swt.graphics.Image getImage(Object element) {
			return null;
		}
		public void dispose() {
		}
		public String getText(Object element) {
			return (String) element;
		}
		public boolean isLabelProperty(Object element, String property) {
			return false;
		}
		public void addListener(ILabelProviderListener listener) {
		}
		public void removeListener(ILabelProviderListener listener) {
		}
	}
}

package org.eclipse.ui.internal.ide.dialogs;

import java.util.Comparator;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.FilteredItemsSelectionDialog;
import org.eclipse.ui.internal.ide.IDEWorkbenchMessages;
import org.eclipse.ui.internal.ide.IDEWorkbenchPlugin;
import org.eclipse.ui.internal.ide.misc.InitialsSearchPattern;
import org.eclipse.ui.model.WorkbenchLabelProvider;

public class SelectProjectDialog extends FilteredItemsSelectionDialog {

	private static final String DIALOG_SETTINGS = "dev.dietermai.projectselect.dialog.settings"; //$NON-NLS-1$


	private List<IProject> projects;

	public SelectProjectDialog(Shell parentShell, List<IProject> projects) {
		super(parentShell, true);
		super.setTitle(IDEWorkbenchMessages.SelectProjectDialog_title);
		super.setMessage(IDEWorkbenchMessages.SelectProjectDialog_message);

		WorkbenchLabelProvider labelProvider = new WorkbenchLabelProvider();
		super.setListLabelProvider(labelProvider);
		super.setDetailsLabelProvider(labelProvider);

		this.setData(projects);
	}

	@Override
	protected Control createExtendedContentArea(Composite parent) {
		return null; // no need for this
	}

	@Override
	protected IDialogSettings getDialogSettings() {
		IDialogSettings settings = getDialogSettings_internal().getSection(DIALOG_SETTINGS);
		if (settings == null) {
			settings = getDialogSettings_internal().addNewSection(DIALOG_SETTINGS);
		}
		return settings;
	}

	private IDialogSettings getDialogSettings_internal() {
		return IDEWorkbenchPlugin.getDefault().getDialogSettings();
	}

	@Override
	protected IStatus validateItem(Object item) {
		return Status.OK_STATUS;
	}

	@Override
	protected ItemsFilter createFilter() {
		return new ProjectSelectItemsFilter();
	}

	@Override
	protected Comparator<?> getItemsComparator() {
		return (arg0, arg1) -> arg0.toString().compareTo(arg1.toString());
	}

	@Override
	protected void fillContentProvider(AbstractContentProvider contentProvider, ItemsFilter itemsFilter,
			IProgressMonitor progressMonitor) {
		projects.forEach(project -> contentProvider.add(project, itemsFilter));
	}

	@Override
	public String getElementName(Object item) {
		if (item instanceof IProject) {
			return ((IProject) item).getName();
		}
		return null;
	}

	private void setData(List<IProject> projects) {
		this.projects = projects;
	}

	private class ProjectSelectItemsFilter extends ItemsFilter {

		private InitialsSearchPattern initalSearchPattern = new InitialsSearchPattern();

		@Override
		public boolean matchItem(Object item) {
			if (item == null) {
				return false;
			} else if (item instanceof IProject) {
				boolean match = matches(((IProject) item).getName());
				match |= initalSearchPattern.checkForInitalMatch(patternMatcher.getPattern(),
						((IProject) item).getName());
				return match;
			} else {
				return false;
			}
		}

		@Override
		public boolean isConsistentItem(Object item) {
			return true;
		}
	}
}

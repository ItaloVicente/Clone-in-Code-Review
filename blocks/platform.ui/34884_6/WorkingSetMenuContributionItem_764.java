package org.eclipse.ui.internal;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.IWorkingSet;
import org.eclipse.ui.IWorkingSetManager;
import org.eclipse.ui.WorkbenchException;
import org.eclipse.ui.XMLMemento;
import org.eclipse.ui.statushandlers.IStatusAdapterConstants;
import org.eclipse.ui.statushandlers.StatusAdapter;
import org.eclipse.ui.statushandlers.StatusManager;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleListener;

public class WorkingSetManager extends AbstractWorkingSetManager implements
		IWorkingSetManager, BundleListener {

	public static final String WORKING_SET_STATE_FILENAME = "workingsets.xml"; //$NON-NLS-1$

	private boolean restoreInProgress;

	private boolean savePending;

	public WorkingSetManager(BundleContext context) {
		super(context);
	}

	@Override
	public void addRecentWorkingSet(IWorkingSet workingSet) {
		internalAddRecentWorkingSet(workingSet);
		saveState();
	}

	@Override
	public void addWorkingSet(IWorkingSet workingSet) {
		super.addWorkingSet(workingSet);
		saveState();
	}

	private File getWorkingSetStateFile() {
		IPath path = WorkbenchPlugin.getDefault().getDataLocation();
		if (path == null) {
			return null;
		}
		path = path.append(WORKING_SET_STATE_FILENAME);
		return path.toFile();
	}

	@Override
	public void removeWorkingSet(IWorkingSet workingSet) {
		if (internalRemoveWorkingSet(workingSet)) {
			saveState();
		}
	}

	public void restoreState() {
		File stateFile = getWorkingSetStateFile();

		if (stateFile != null && stateFile.exists()) {
			try {
				restoreInProgress = true;

				FileInputStream input = new FileInputStream(stateFile);
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(input, "utf-8")); //$NON-NLS-1$

				IMemento memento = XMLMemento.createReadRoot(reader);
				restoreWorkingSetState(memento);
				restoreMruList(memento);
				reader.close();
			} catch (IOException e) {
				handleInternalError(
						e,
						WorkbenchMessages.ProblemRestoringWorkingSetState_title,
						WorkbenchMessages.ProblemRestoringWorkingSetState_message);
			} catch (WorkbenchException e) {
				handleInternalError(
						e,
						WorkbenchMessages.ProblemRestoringWorkingSetState_title,
						WorkbenchMessages.ProblemRestoringWorkingSetState_message);
			} finally {
				restoreInProgress = false;
			}

			if (savePending) {
				saveState();
				savePending = false;
			}
		}
	}

	private void saveState() {
		if (restoreInProgress) {
			savePending = true;
			return;
		}

		File stateFile = getWorkingSetStateFile();
		if (stateFile == null) {
			return;
		}
		try {
			saveState(stateFile);
		} catch (IOException e) {
			stateFile.delete();
			handleInternalError(e,
					WorkbenchMessages.ProblemSavingWorkingSetState_title,
					WorkbenchMessages.ProblemSavingWorkingSetState_message);
		}
	}

	@Override
	public void workingSetChanged(IWorkingSet changedWorkingSet,
			String propertyChangeId, Object oldValue) {
		saveState();
		super.workingSetChanged(changedWorkingSet, propertyChangeId, oldValue);
	}

	private void handleInternalError(Exception exp, String title, String message) {
		Status status = new Status(IStatus.ERROR, WorkbenchPlugin.PI_WORKBENCH,
				message, exp);
		StatusAdapter sa = new StatusAdapter(status);
		sa.setProperty(IStatusAdapterConstants.TITLE_PROPERTY, title);
		StatusManager.getManager().handle(sa,
				StatusManager.SHOW | StatusManager.LOG);
	}

	@Override
	protected void restoreWorkingSetState(IMemento memento) {
		super.restoreWorkingSetState(memento);
		saveState();
	}
}

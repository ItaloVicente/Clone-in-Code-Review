/*******************************************************************************
 * Copyright (c) 2003, 2016 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Lars Vogel <Lars.Vogel@gmail.com> - Bug 430694
 *     Christian Georgi (SAP)            - Bug 432480
 *     Patrik Suzzi <psuzzi@gmail.com>   - Bug 490700, 502050
 *     Vasili Gulevich                   - Bug 501404
 *******************************************************************************/
package org.eclipse.ui.internal.ide.application;

import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import org.eclipse.core.internal.resources.Workspace;
import org.eclipse.core.net.proxy.IProxyService;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IBundleGroup;
import org.eclipse.core.runtime.IBundleGroupProvider;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.ProgressMonitorWrapper;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.ui.internal.workbench.E4Workbench;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.TrayDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.util.Policy;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.application.IWorkbenchConfigurer;
import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.application.WorkbenchAdvisor;
import org.eclipse.ui.application.WorkbenchWindowAdvisor;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.internal.ISelectionConversionService;
import org.eclipse.ui.internal.PluginActionBuilder;
import org.eclipse.ui.internal.Workbench;
import org.eclipse.ui.internal.ide.AboutInfo;
import org.eclipse.ui.internal.ide.IDEInternalPreferences;
import org.eclipse.ui.internal.ide.IDEInternalWorkbenchImages;
import org.eclipse.ui.internal.ide.IDESelectionConversionService;
import org.eclipse.ui.internal.ide.IDEWorkbenchActivityHelper;
import org.eclipse.ui.internal.ide.IDEWorkbenchErrorHandler;
import org.eclipse.ui.internal.ide.IDEWorkbenchMessages;
import org.eclipse.ui.internal.ide.IDEWorkbenchPlugin;
import org.eclipse.ui.internal.ide.undo.WorkspaceUndoMonitor;
import org.eclipse.ui.internal.progress.ProgressMonitorJobsDialog;
import org.eclipse.ui.progress.IProgressService;
import org.eclipse.ui.statushandlers.AbstractStatusHandler;
import org.osgi.framework.Bundle;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.Version;

import com.ibm.icu.text.Collator;

/**
 * IDE-specified workbench advisor which configures the workbench for use as an
 * IDE.
 * <p>
 * Note: This class replaces <code>org.eclipse.ui.internal.Workbench</code>.
 * </p>
 *
 * @since 3.0
 */
public class IDEWorkbenchAdvisor extends WorkbenchAdvisor {


	/**
	 * The dialog setting key to access the known installed features since the
	 * last time the workbench was run.
	 */

	private static IDEWorkbenchAdvisor workbenchAdvisor = null;

	/**
	 * Ordered map of versioned feature ids -> info that are new for this
	 * session; <code>null</code> if uninitialized. Key type:
	 * <code>String</code>, Value type: <code>AboutInfo</code>.
	 */
	private Map<String, AboutInfo> newlyAddedBundleGroups;

	/**
	 * Array of <code>AboutInfo</code> for all new installed features that
	 * specify a welcome perspective.
	 */
	private AboutInfo[] welcomePerspectiveInfos = null;

	/**
	 * Helper for managing activites in response to workspace changes.
	 */
	private IDEWorkbenchActivityHelper activityHelper = null;

	/**
	 * Helper for managing work that is performed when the system is otherwise
	 * idle.
	 */
	private IDEIdleHelper idleHelper;

	private Listener settingsChangeListener;

	/**
	 * Support class for monitoring workspace changes and periodically
	 * validating the undo history
	 */
	private WorkspaceUndoMonitor workspaceUndoMonitor;

	/**
	 * The IDE workbench error handler.
	 */
	private AbstractStatusHandler ideWorkbenchErrorHandler;

	/**
	 * Helper class used to process delayed events.
	 */
	private final DelayedEventsProcessor delayedEventsProcessor;

	private static boolean jfaceComparatorIsSet = false;

	/**
	 * Base wait time between workspace lock attempts
	 */
	private final int workspaceWaitDelay;

	private final Listener closeListener = new Listener() {
		@Override
		public void handleEvent(Event event) {
			boolean doExit = IDEWorkbenchWindowAdvisor.promptOnExit(null);
			event.doit = doExit;
			if (!doExit)
				event.type = SWT.None;
		}
	};

	/**
	 * Creates a new workbench advisor instance.
	 */
	public IDEWorkbenchAdvisor() {
		this(1000, null);
	}

	IDEWorkbenchAdvisor(int workspaceWaitDelay, DelayedEventsProcessor processor) {
		super();
		this.workspaceWaitDelay = workspaceWaitDelay;
		if (workbenchAdvisor != null) {
			throw new IllegalStateException();
		}
		workbenchAdvisor = this;

		this.delayedEventsProcessor = processor;
		Display.getDefault().addListener(SWT.Close, closeListener);
	}

	/**
	 * Creates a new workbench advisor instance supporting delayed file open.
	 * @param processor helper class used to process delayed events
	 */
	public IDEWorkbenchAdvisor(DelayedEventsProcessor processor) {
		this(1000, processor);
	}

	@Override
	public void initialize(IWorkbenchConfigurer configurer) {

		PluginActionBuilder.setAllowIdeLogging(true);

		configurer.setSaveAndRestore(true);

		IDE.registerAdapters();

		declareWorkbenchImages();

		activityHelper = IDEWorkbenchActivityHelper.getInstance();

		idleHelper = new IDEIdleHelper(configurer);

		workspaceUndoMonitor = WorkspaceUndoMonitor.getInstance();

		TrayDialog.setDialogHelpAvailable(true);

		setWorkspaceNameDefault();

		if (!jfaceComparatorIsSet) {
			Policy.setComparator(Collator.getInstance());
			jfaceComparatorIsSet = true;
		}

	}

	@Override
	public void preStartup() {

		Job.getJobManager().suspend();

		IProgressService service = PlatformUI.getWorkbench()
				.getProgressService();
		ImageDescriptor newImage = IDEInternalWorkbenchImages
				.getImageDescriptor(IDEInternalWorkbenchImages.IMG_ETOOL_BUILD_EXEC);
		service.registerIconForFamily(newImage,
				ResourcesPlugin.FAMILY_MANUAL_BUILD);
		service.registerIconForFamily(newImage,
				ResourcesPlugin.FAMILY_AUTO_BUILD);
	}

	@Override
	public void postStartup() {
		try {
			refreshFromLocal();
			activateProxyService();
			((Workbench) PlatformUI.getWorkbench()).registerService(
					ISelectionConversionService.class,
					new IDESelectionConversionService());

			initializeSettingsChangeListener();
			Display.getCurrent().addListener(SWT.Settings,
					settingsChangeListener);
			Job.getJobManager().resume();
		}
	}

	/**
	 * Activate the proxy service by obtaining it.
	 */
	private void activateProxyService() {
		Object proxyService = null;
		if (bundle != null) {
			ServiceReference<IProxyService> ref = bundle.getBundleContext().getServiceReference(IProxyService.class);
			if (ref != null)
				proxyService = bundle.getBundleContext().getService(ref);
		}
		if (proxyService == null) {
		}
	}

	/**
	 * Initialize the listener for settings changes.
	 */
	private void initializeSettingsChangeListener() {
		settingsChangeListener = new Listener() {

			boolean currentHighContrast = Display.getCurrent()
					.getHighContrast();

			@Override
			public void handleEvent(org.eclipse.swt.widgets.Event event) {
				if (Display.getCurrent().getHighContrast() == currentHighContrast)
					return;

				currentHighContrast = !currentHighContrast;

				if (new MessageDialog(null, IDEWorkbenchMessages.SystemSettingsChange_title, null,
						IDEWorkbenchMessages.SystemSettingsChange_message, MessageDialog.QUESTION, 1,
						IDEWorkbenchMessages.SystemSettingsChange_yes, IDEWorkbenchMessages.SystemSettingsChange_no)
								.open() == Window.OK) {
					PlatformUI.getWorkbench().restart();
				}
			}
		};

	}

	@Override
	public void postShutdown() {
		Display.getDefault().removeListener(SWT.Close, closeListener);
		if (activityHelper != null) {
			activityHelper.shutdown();
			activityHelper = null;
		}
		if (idleHelper != null) {
			idleHelper.shutdown();
			idleHelper = null;
		}
		if (workspaceUndoMonitor != null) {
			workspaceUndoMonitor.shutdown();
			workspaceUndoMonitor = null;
		}

		IWorkspace workspace = IDEWorkbenchPlugin.getPluginWorkspace();

		final Runnable disconnectFromWorkspace = new Runnable() {

			int attempts;

			@Override
			public void run() {
				if (isWorkspaceLocked(workspace)) {
					if (attempts < 3) {
						attempts++;
						IDEWorkbenchPlugin.log(null, createErrorStatus("Workspace is locked, waiting...")); //$NON-NLS-1$
						Display.getCurrent().timerExec(workspaceWaitDelay * attempts, this);
					} else {
						IDEWorkbenchPlugin.log(null, createErrorStatus("Workspace is locked and can't be saved.")); //$NON-NLS-1$
					}
					return;
				}
				disconnectFromWorkspace();
			}

			IStatus createErrorStatus(String exceptionMessage) {
				return new Status(IStatus.ERROR, IDEWorkbenchPlugin.IDE_WORKBENCH, 1,
						IDEWorkbenchMessages.ProblemsSavingWorkspace, new IllegalStateException(exceptionMessage));
			}
		};

		if (workspace != null) {
			if (isWorkspaceLocked(workspace)) {
				Display.getCurrent().asyncExec(disconnectFromWorkspace);
			} else {
				disconnectFromWorkspace.run();
			}
		}
		workbenchAdvisor = null;
	}

	private boolean isWorkspaceLocked(IWorkspace workspace) {
		ISchedulingRule currentRule = Job.getJobManager().currentRule();
		return currentRule != null && currentRule.isConflicting(workspace.getRoot());
	}

	@Override
	public boolean preShutdown() {
		Display.getCurrent().removeListener(SWT.Settings,
				settingsChangeListener);
		return super.preShutdown();
	}

	@Override
	public WorkbenchWindowAdvisor createWorkbenchWindowAdvisor(
			IWorkbenchWindowConfigurer configurer) {
		return new IDEWorkbenchWindowAdvisor(this, configurer);
	}

	/**
	 * Return true if the intro plugin is present and false otherwise.
	 *
	 * @return boolean
	 */
	public boolean hasIntro() {
		return getWorkbenchConfigurer().getWorkbench().getIntroManager()
				.hasIntro();
	}

	private void refreshFromLocal() {
		String[] commandLineArgs = Platform.getCommandLineArgs();
		IPreferenceStore store = IDEWorkbenchPlugin.getDefault()
				.getPreferenceStore();
		boolean refresh = store
				.getBoolean(IDEInternalPreferences.REFRESH_WORKSPACE_ON_STARTUP);
		if (!refresh) {
			return;
		}

		for (String commandLineArg : commandLineArgs) {
				return;
			}
		}

		final IContainer root = ResourcesPlugin.getWorkspace().getRoot();
		Job job = new WorkspaceJob(IDEWorkbenchMessages.Workspace_refreshing) {
			@Override
			public IStatus runInWorkspace(IProgressMonitor monitor)
					throws CoreException {
				root.refreshLocal(IResource.DEPTH_INFINITE, monitor);
				return Status.OK_STATUS;
			}
		};
		job.setRule(root);
		job.schedule();
	}

	private class CancelableProgressMonitorWrapper extends
			ProgressMonitorWrapper {
		private double total = 0;
		private ProgressMonitorJobsDialog dialog;

		CancelableProgressMonitorWrapper(IProgressMonitor monitor,
				ProgressMonitorJobsDialog dialog) {
			super(monitor);
			this.dialog = dialog;
		}

		@Override
		public void internalWorked(double work) {
			super.internalWorked(work);
			total += work;
			updateProgressDetails();
		}

		@Override
		public void worked(int work) {
			super.worked(work);
			total += work;
			updateProgressDetails();
		}

		@Override
		public void beginTask(String name, int totalWork) {
			super.beginTask(name, totalWork);
			subTask(IDEWorkbenchMessages.IDEWorkbenchAdvisor_preHistoryCompaction);
		}

		private void updateProgressDetails() {
			if (!isCanceled() && Math.abs(total - 4.0) < 0.0001 /* right before history compacting */) {
				subTask(IDEWorkbenchMessages.IDEWorkbenchAdvisor_cancelHistoryPruning);
				dialog.setCancelable(true);
			}
			if (Math.abs(total - 5.0) < 0.0001 /* history compacting finished */) {
				subTask(IDEWorkbenchMessages.IDEWorkbenchAdvisor_postHistoryCompaction);
				dialog.setCancelable(false);
			}
		}
	}

	private class CancelableProgressMonitorJobsDialog extends
			ProgressMonitorJobsDialog {

		public CancelableProgressMonitorJobsDialog(Shell parent) {
			super(parent);
		}

		@Override
		protected void createButtonsForButtonBar(Composite parent) {
			super.createButtonsForButtonBar(parent);
			registerCancelButtonListener();
		}

		public void registerCancelButtonListener() {
			cancel.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
				}
			});
		}
	}

	/**
	 * Disconnect from the core workspace.
	 *
	 * Locks workspace in a background thread, should not be called while
	 * holding any workspace locks.
	 */
	private void disconnectFromWorkspace() {
		final MultiStatus status = new MultiStatus(
				IDEWorkbenchPlugin.IDE_WORKBENCH, 1,
				IDEWorkbenchMessages.ProblemSavingWorkbench, null);
		try {
			final ProgressMonitorJobsDialog p = new CancelableProgressMonitorJobsDialog(
					null);

			final boolean applyPolicy = ResourcesPlugin.getWorkspace()
					.getDescription().isApplyFileStatePolicy();

			IRunnableWithProgress runnable = new IRunnableWithProgress() {
				@Override
				public void run(IProgressMonitor monitor) {
					try {
						if (applyPolicy)
							monitor = new CancelableProgressMonitorWrapper(
									monitor, p);

						status.merge(((Workspace) ResourcesPlugin
								.getWorkspace()).save(true, true, monitor));
					} catch (CoreException e) {
						status.merge(e.getStatus());
					}
				}
			};

			p.run(true, false, runnable);
		} catch (InvocationTargetException e) {
			status
					.merge(new Status(IStatus.ERROR,
							IDEWorkbenchPlugin.IDE_WORKBENCH, 1,
							IDEWorkbenchMessages.InternalError, e
									.getTargetException()));
		} catch (InterruptedException e) {
			status.merge(new Status(IStatus.ERROR,
					IDEWorkbenchPlugin.IDE_WORKBENCH, 1,
					IDEWorkbenchMessages.InternalError, e));
		}
		ErrorDialog.openError(null,
				IDEWorkbenchMessages.ProblemsSavingWorkspace, null, status,
				IStatus.ERROR | IStatus.WARNING);
		if (!status.isOK()) {
			IDEWorkbenchPlugin.log(
					IDEWorkbenchMessages.ProblemsSavingWorkspace, status);
		}
	}

	@Override
	public IAdaptable getDefaultPageInput() {
		return ResourcesPlugin.getWorkspace().getRoot();
	}

	@Override
	public String getInitialWindowPerspectiveId() {
		int index = PlatformUI.getWorkbench().getWorkbenchWindowCount() - 1;

		String perspectiveId = null;
		AboutInfo[] welcomeInfos = getWelcomePerspectiveInfos();
		if (index >= 0 && welcomeInfos != null && index < welcomeInfos.length) {
			perspectiveId = welcomeInfos[index].getWelcomePerspectiveId();
		}
		if (perspectiveId == null) {
			perspectiveId = IDE.RESOURCE_PERSPECTIVE_ID;
		}
		return perspectiveId;
	}

	/**
	 * Returns the map of versioned feature ids -> info object for all installed
	 * features. The format of the versioned feature id (the key of the map) is
	 * featureId + ":" + versionId.
	 *
	 * @return map of versioned feature ids -> info object (key type:
	 *         <code>String</code>, value type: <code>AboutInfo</code>)
	 * @since 3.0
	 */
	private Map<String, AboutInfo> computeBundleGroupMap() {
		Map<String, AboutInfo> ids = new TreeMap<>();

		IBundleGroupProvider[] providers = Platform.getBundleGroupProviders();
		for (IBundleGroupProvider provider : providers) {
			IBundleGroup[] groups = provider.getBundleGroups();
			for (IBundleGroup group : groups) {
				AboutInfo info = new AboutInfo(group);

				String version = info.getVersionId();
						: new Version(version).toString();

				ids.put(versionedFeature, info);
			}
		}

		return ids;
	}

	/**
	 * Returns the ordered map of versioned feature ids -> AboutInfo that are
	 * new for this session.
	 *
	 * @return ordered map of versioned feature ids (key type:
	 *         <code>String</code>) -> infos (value type:
	 *         <code>AboutInfo</code>).
	 */
	public Map<String, AboutInfo> getNewlyAddedBundleGroups() {
		if (newlyAddedBundleGroups == null) {
			newlyAddedBundleGroups = createNewBundleGroupsMap();
		}
		return newlyAddedBundleGroups;
	}

	/**
	 * Updates the old features setting and returns a map of new features.
	 */
	private Map<String, AboutInfo> createNewBundleGroupsMap() {
		IDialogSettings settings = IDEWorkbenchPlugin.getDefault()
				.getDialogSettings();
		String[] previousFeaturesArray = settings.getArray(INSTALLED_FEATURES);

		Map<String, AboutInfo> bundleGroups = computeBundleGroupMap();
		String[] currentFeaturesArray = new String[bundleGroups.size()];
		bundleGroups.keySet().toArray(currentFeaturesArray);
		settings.put(INSTALLED_FEATURES, currentFeaturesArray);

		if (previousFeaturesArray != null) {
			for (String element : previousFeaturesArray) {
				bundleGroups.remove(element);
			}
		}

		return bundleGroups;
	}

	/**
	 * Sets the default value of the preference controlling the workspace name
	 * displayed in the window title to the name of the workspace directory.
	 * This preference cannot be set in the preference initializer because the
	 * workspace directory may not be known when the preference initializer is
	 * called.
	 */
	private static void setWorkspaceNameDefault() {
		IPreferenceStore preferences = IDEWorkbenchPlugin.getDefault().getPreferenceStore();
		String workspaceNameDefault = preferences.getDefaultString(IDEInternalPreferences.WORKSPACE_NAME);
		if (workspaceNameDefault != null && !workspaceNameDefault.isEmpty())
		IPath workspaceDir = Platform.getLocation();
		if (workspaceDir == null)
			return;
		String workspaceName = workspaceDir.lastSegment();
		if (workspaceName == null)
			return;
		preferences.setDefault(IDEInternalPreferences.WORKSPACE_NAME, workspaceName);
	}

	/**
	 * Declares all IDE-specific workbench images. This includes both "shared"
	 * images (named in {@link org.eclipse.ui.ide.IDE.SharedImages}) and internal images (named in
	 * {@link org.eclipse.ui.internal.ide.IDEInternalWorkbenchImages}).
	 *
	 * @see IWorkbenchConfigurer#declareImage
	 */
	private void declareWorkbenchImages() {





		Bundle ideBundle = Platform.getBundle(IDEWorkbenchPlugin.IDE_WORKBENCH);

		declareWorkbenchImage(ideBundle,
				IDEInternalWorkbenchImages.IMG_ETOOL_BUILD_EXEC, PATH_ETOOL
						+ "build_exec.png", false); //$NON-NLS-1$
		declareWorkbenchImage(ideBundle,
				IDEInternalWorkbenchImages.IMG_ETOOL_BUILD_EXEC_HOVER,
				PATH_ETOOL + "build_exec.png", false); //$NON-NLS-1$
		declareWorkbenchImage(ideBundle,
				IDEInternalWorkbenchImages.IMG_ETOOL_BUILD_EXEC_DISABLED,
				PATH_DTOOL + "build_exec.png", false); //$NON-NLS-1$

		declareWorkbenchImage(ideBundle,
				IDEInternalWorkbenchImages.IMG_ETOOL_SEARCH_SRC, PATH_ETOOL
						+ "search_src.png", false); //$NON-NLS-1$
		declareWorkbenchImage(ideBundle,
				IDEInternalWorkbenchImages.IMG_ETOOL_SEARCH_SRC_HOVER,
				PATH_ETOOL + "search_src.png", false); //$NON-NLS-1$
		declareWorkbenchImage(ideBundle,
				IDEInternalWorkbenchImages.IMG_ETOOL_SEARCH_SRC_DISABLED,
				PATH_DTOOL + "search_src.png", false); //$NON-NLS-1$

		declareWorkbenchImage(ideBundle,
				IDEInternalWorkbenchImages.IMG_ETOOL_NEXT_NAV, PATH_ETOOL
						+ "next_nav.png", false); //$NON-NLS-1$

		declareWorkbenchImage(ideBundle,
				IDEInternalWorkbenchImages.IMG_ETOOL_PREVIOUS_NAV, PATH_ETOOL
						+ "prev_nav.png", false); //$NON-NLS-1$

		declareWorkbenchImage(ideBundle,
				IDEInternalWorkbenchImages.IMG_WIZBAN_NEWPRJ_WIZ, PATH_WIZBAN
						+ "newprj_wiz.png", false); //$NON-NLS-1$
		declareWorkbenchImage(ideBundle,
				IDEInternalWorkbenchImages.IMG_WIZBAN_NEWFOLDER_WIZ,
				PATH_WIZBAN + "newfolder_wiz.png", false); //$NON-NLS-1$
		declareWorkbenchImage(ideBundle,
				IDEInternalWorkbenchImages.IMG_WIZBAN_NEWFILE_WIZ, PATH_WIZBAN
						+ "newfile_wiz.png", false); //$NON-NLS-1$

		declareWorkbenchImage(ideBundle,
				IDEInternalWorkbenchImages.IMG_WIZBAN_IMPORTDIR_WIZ,
				PATH_WIZBAN + "importdir_wiz.png", false); //$NON-NLS-1$
		declareWorkbenchImage(ideBundle,
				IDEInternalWorkbenchImages.IMG_WIZBAN_IMPORTZIP_WIZ,
				PATH_WIZBAN + "importzip_wiz.png", false); //$NON-NLS-1$

		declareWorkbenchImage(ideBundle,
				IDEInternalWorkbenchImages.IMG_WIZBAN_EXPORTDIR_WIZ,
				PATH_WIZBAN + "exportdir_wiz.png", false); //$NON-NLS-1$
		declareWorkbenchImage(ideBundle,
				IDEInternalWorkbenchImages.IMG_WIZBAN_EXPORTZIP_WIZ,
				PATH_WIZBAN + "exportzip_wiz.png", false); //$NON-NLS-1$

		declareWorkbenchImage(ideBundle,
				IDEInternalWorkbenchImages.IMG_WIZBAN_RESOURCEWORKINGSET_WIZ,
				PATH_WIZBAN + "workset_wiz.png", false); //$NON-NLS-1$

		declareWorkbenchImage(ideBundle,
				IDEInternalWorkbenchImages.IMG_DLGBAN_SAVEAS_DLG, PATH_WIZBAN
						+ "saveas_wiz.png", false); //$NON-NLS-1$

		declareWorkbenchImage(ideBundle,
				IDEInternalWorkbenchImages.IMG_DLGBAN_QUICKFIX_DLG, PATH_WIZBAN
						+ "quick_fix.png", false); //$NON-NLS-1$

		declareWorkbenchImage(ideBundle, IDE.SharedImages.IMG_OBJ_PROJECT,
				PATH_OBJECT + "prj_obj.png", true); //$NON-NLS-1$
		declareWorkbenchImage(ideBundle,
				IDE.SharedImages.IMG_OBJ_PROJECT_CLOSED, PATH_OBJECT
						+ "cprj_obj.png", true); //$NON-NLS-1$
		declareWorkbenchImage(ideBundle, IDE.SharedImages.IMG_OPEN_MARKER,
				PATH_ELOCALTOOL + "gotoobj_tsk.png", true); //$NON-NLS-1$


		declareWorkbenchImage(ideBundle,
				IDEInternalWorkbenchImages.IMG_ELCL_QUICK_FIX_ENABLED,
				PATH_ELOCALTOOL + "smartmode_co.png", true); //$NON-NLS-1$

		declareWorkbenchImage(ideBundle,
				IDEInternalWorkbenchImages.IMG_DLCL_QUICK_FIX_DISABLED,
				PATH_DLOCALTOOL + "smartmode_co.png", true); //$NON-NLS-1$

		declareWorkbenchImage(ideBundle,
				IDEInternalWorkbenchImages.IMG_OBJS_FIXABLE_WARNING,
				PATH_OBJECT + "quickfix_warning_obj.png", true); //$NON-NLS-1$
		declareWorkbenchImage(ideBundle,
				IDEInternalWorkbenchImages.IMG_OBJS_FIXABLE_ERROR,
				PATH_OBJECT + "quickfix_error_obj.png", true); //$NON-NLS-1$
		declareWorkbenchImage(ideBundle,
				IDEInternalWorkbenchImages.IMG_OBJS_FIXABLE_INFO,
				PATH_OBJECT + "quickfix_info_obj.png", true); //$NON-NLS-1$



		declareWorkbenchImage(ideBundle, IDE.SharedImages.IMG_OBJS_TASK_TSK,
				PATH_OBJECT + "taskmrk_tsk.png", true); //$NON-NLS-1$
		declareWorkbenchImage(ideBundle, IDE.SharedImages.IMG_OBJS_BKMRK_TSK,
				PATH_OBJECT + "bkmrk_tsk.png", true); //$NON-NLS-1$

		declareWorkbenchImage(ideBundle,
				IDEInternalWorkbenchImages.IMG_OBJS_COMPLETE_TSK, PATH_OBJECT
						+ "complete_tsk.png", true); //$NON-NLS-1$
		declareWorkbenchImage(ideBundle,
				IDEInternalWorkbenchImages.IMG_OBJS_INCOMPLETE_TSK, PATH_OBJECT
						+ "incomplete_tsk.png", true); //$NON-NLS-1$
		declareWorkbenchImage(ideBundle,
				IDEInternalWorkbenchImages.IMG_OBJS_WELCOME_ITEM, PATH_OBJECT
						+ "welcome_item.png", true); //$NON-NLS-1$
		declareWorkbenchImage(ideBundle,
				IDEInternalWorkbenchImages.IMG_OBJS_WELCOME_BANNER, PATH_OBJECT
						+ "welcome_banner.png", true); //$NON-NLS-1$
		declareWorkbenchImage(ideBundle,
				IDEInternalWorkbenchImages.IMG_OBJS_ERROR_PATH, PATH_OBJECT
						+ "error_tsk.png", true); //$NON-NLS-1$
		declareWorkbenchImage(ideBundle,
				IDEInternalWorkbenchImages.IMG_OBJS_WARNING_PATH, PATH_OBJECT
						+ "warn_tsk.png", true); //$NON-NLS-1$
		declareWorkbenchImage(ideBundle,
				IDEInternalWorkbenchImages.IMG_OBJS_INFO_PATH, PATH_OBJECT
						+ "info_tsk.png", true); //$NON-NLS-1$

		declareWorkbenchImage(ideBundle,
				IDEInternalWorkbenchImages.IMG_LCL_FLAT_LAYOUT, PATH_ELOCALTOOL
						+ "flatLayout.png", true); //$NON-NLS-1$
		declareWorkbenchImage(ideBundle,
				IDEInternalWorkbenchImages.IMG_LCL_HIERARCHICAL_LAYOUT,
				PATH_ELOCALTOOL + "hierarchicalLayout.png", true); //$NON-NLS-1$
		declareWorkbenchImage(ideBundle,
				IDEInternalWorkbenchImages.IMG_ETOOL_PROBLEM_CATEGORY,
				PATH_ETOOL + "problem_category.png", true); //$NON-NLS-1$

		declareWorkbenchImage(ideBundle,
				IDEInternalWorkbenchImages.IMG_ETOOL_PROBLEMS_VIEW,
				PATH_EVIEW + "problems_view.png", true); //$NON-NLS-1$
		declareWorkbenchImage(ideBundle,
				IDEInternalWorkbenchImages.IMG_ETOOL_PROBLEMS_VIEW_ERROR,
				PATH_EVIEW + "problems_view_error.png", true); //$NON-NLS-1$
		declareWorkbenchImage(ideBundle,
				IDEInternalWorkbenchImages.IMG_ETOOL_PROBLEMS_VIEW_WARNING,
				PATH_EVIEW + "problems_view_warning.png", true); //$NON-NLS-1$
		declareWorkbenchImage(ideBundle,
				IDEInternalWorkbenchImages.IMG_ETOOL_PROBLEMS_VIEW_INFO,
				PATH_EVIEW + "problems_view_info.png", true); //$NON-NLS-1$


	}

	/**
	 * Declares an IDE-specific workbench image.
	 *
	 * @param symbolicName
	 *            the symbolic name of the image
	 * @param path
	 *            the path of the image file; this path is relative to the base
	 *            of the IDE plug-in
	 * @param shared
	 *            <code>true</code> if this is a shared image, and
	 *            <code>false</code> if this is not a shared image
	 * @see IWorkbenchConfigurer#declareImage
	 */
	private void declareWorkbenchImage(Bundle ideBundle, String symbolicName,
			String path, boolean shared) {
		URL url = FileLocator.find(ideBundle, new Path(path), null);
		ImageDescriptor desc = ImageDescriptor.createFromURL(url);
		getWorkbenchConfigurer().declareImage(symbolicName, desc, shared);
	}

	@Override
	public String getMainPreferencePageId() {
		return WORKBENCH_PREFERENCE_CATEGORY_ID;
	}

	/**
	 * Returns the location specified in command line when -showlocation is
	 * defined. Otherwise returns null
	 *
	 * @return
	 */
	public String getCommandLineLocation() {
		IEclipseContext context = getWorkbenchConfigurer().getWorkbench().getService(IEclipseContext.class);
		return context != null ? (String) context.get(E4Workbench.FORCED_SHOW_LOCATION) : null;
	}

	/**
	 * Returns the location to show in the window title, depending on a
	 * {@link IDEInternalPreferences#SHOW_LOCATION} user preference. Note that
	 * this may be overridden by the '-showlocation' command line argument.
	 *
	 * @return the location string, or <code>null</code> if the location is not
	 *         being shown
	 */
	public String getWorkspaceLocation() {
		String location = getCommandLineLocation();
		if (location != null) {
			return location;
		}
		if (IDEWorkbenchPlugin.getDefault().getPreferenceStore().getBoolean(IDEInternalPreferences.SHOW_LOCATION)) {
			return Platform.getLocation().toOSString();
		}
		return null;
	}

	/**
	 * @return the welcome perspective infos, or <code>null</code> if none or
	 *         if they should be ignored due to the new intro being present
	 */
	public AboutInfo[] getWelcomePerspectiveInfos() {
		if (welcomePerspectiveInfos == null) {
			if (!hasIntro()) {
				Map<String, AboutInfo> m = getNewlyAddedBundleGroups();
				ArrayList<AboutInfo> list = new ArrayList<>(m.size());
				for (AboutInfo info : m.values()) {
					if (info != null && info.getWelcomePerspectiveId() != null
							&& info.getWelcomePageURL() != null) {
						list.add(info);
					}
				}
				welcomePerspectiveInfos = new AboutInfo[list.size()];
				list.toArray(welcomePerspectiveInfos);
			}
		}
		return welcomePerspectiveInfos;
	}

	@Override
	public synchronized AbstractStatusHandler getWorkbenchErrorHandler() {
		if (ideWorkbenchErrorHandler == null) {
			ideWorkbenchErrorHandler = new IDEWorkbenchErrorHandler(
					getWorkbenchConfigurer());
		}
		return ideWorkbenchErrorHandler;
	}

	@Override
	public void eventLoopIdle(Display display) {
		if (delayedEventsProcessor != null)
			delayedEventsProcessor.catchUp(display);
		super.eventLoopIdle(display);
	}
}

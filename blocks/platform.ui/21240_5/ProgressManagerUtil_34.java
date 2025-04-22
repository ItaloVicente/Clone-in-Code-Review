package org.eclipse.e4.ui.progress.internal;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IProgressMonitorWithBlocking;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.ListenerList;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.IJobChangeListener;
import org.eclipse.core.runtime.jobs.IJobManager;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;
import org.eclipse.core.runtime.jobs.ProgressProvider;
import org.eclipse.e4.ui.progress.IProgressConstants;
import org.eclipse.e4.ui.progress.IProgressService;
import org.eclipse.e4.ui.progress.UIJob;
import org.eclipse.e4.ui.progress.WorkbenchJob;
import org.eclipse.e4.ui.progress.e4new.ExternalServices;
import org.eclipse.e4.ui.progress.legacy.EventLoopProgressMonitor;
import org.eclipse.e4.ui.progress.legacy.PlatformUI;
import org.eclipse.e4.ui.progress.legacy.Policy;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.operation.IRunnableContext;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class ProgressManager extends ProgressProvider implements
		IProgressService {
	public static final QualifiedName PROPERTY_IN_DIALOG = IProgressConstants.PROPERTY_IN_DIALOG;

	private static final String ERROR_JOB = "errorstate.png"; //$NON-NLS-1$

	static final String ERROR_JOB_KEY = "ERROR_JOB"; //$NON-NLS-1$

	private static ProgressManager singleton;

	final private Map jobs = Collections.synchronizedMap(new HashMap());

	final private Map familyListeners = Collections
			.synchronizedMap(new HashMap());

	private ListenerList listeners = new ListenerList();
	
	final IJobChangeListener changeListener;

	static final String PROGRESS_VIEW_NAME = "org.eclipse.e4.ui.progress.ProgressView"; //$NON-NLS-1$

	static final String PROGRESS_FOLDER = "$nl$/icons/full/progress/"; //$NON-NLS-1$

	private static final String SLEEPING_JOB = "sleeping.png"; //$NON-NLS-1$

	private static final String WAITING_JOB = "waiting.png"; //$NON-NLS-1$

	private static final String BLOCKED_JOB = "lockedstate.png"; //$NON-NLS-1$

	public static final String SLEEPING_JOB_KEY = "SLEEPING_JOB"; //$NON-NLS-1$

	public static final String WAITING_JOB_KEY = "WAITING_JOB"; //$NON-NLS-1$

	public static final String BLOCKED_JOB_KEY = "LOCKED_JOB"; //$NON-NLS-1$

	final Map runnableMonitors = Collections.synchronizedMap(new HashMap());

	private Hashtable imageKeyTable = new Hashtable();


	private static final String IMAGE_KEY = "org.eclipse.ui.progress.images"; //$NON-NLS-1$

	public static ProgressManager getInstance() {
		if (singleton == null) {
			singleton = new ProgressManager();
		}
		return singleton;
	}

	public static void shutdownProgressManager() {
		if (singleton == null) {
			return;
		}
		singleton.shutdown();
	}

	class JobMonitor implements IProgressMonitorWithBlocking {
		Job job;

		String currentTaskName;

		IProgressMonitorWithBlocking listener;

		JobMonitor(Job newJob) {
			job = newJob;
		}

		void addProgressListener(IProgressMonitorWithBlocking monitor) {
			listener = monitor;
			JobInfo info = getJobInfo(job);
			TaskInfo currentTask = info.getTaskInfo();
			if (currentTask != null) {
				listener.beginTask(currentTaskName, currentTask.totalWork);
				listener.internalWorked(currentTask.preWork);
			}
		}

		public void beginTask(String taskName, int totalWork) {
			JobInfo info = getJobInfo(job);
			info.beginTask(taskName, totalWork);
			refreshJobInfo(info);
			currentTaskName = taskName;
			if (listener != null) {
				listener.beginTask(taskName, totalWork);
			}
		}

		public void done() {
			JobInfo info = getJobInfo(job);
			info.clearTaskInfo();
			info.clearChildren();
			runnableMonitors.remove(job);
			if (listener != null) {
				listener.done();
			}
		}

		public void internalWorked(double work) {
			JobInfo info = getJobInfo(job);
			if (info.hasTaskInfo()) {
				info.addWork(work);
				refreshJobInfo(info);
			}
			if (listener != null) {
				listener.internalWorked(work);
			}
		}

		public boolean isCanceled() {
			JobInfo info = internalGetJobInfo(job);
			if (info == null)
				return false;
			return info.isCanceled();
		}

		public void setCanceled(boolean value) {
			JobInfo info = getJobInfo(job);
			if (value && !info.isCanceled()) {
				info.cancel();
				if (listener != null) {
					listener.setCanceled(value);
				}
			}
		}

		public void setTaskName(String taskName) {
			JobInfo info = getJobInfo(job);
			if (info.hasTaskInfo()) {
				info.setTaskName(taskName);
			} else {
				beginTask(taskName, 100);
				return;
			}
			info.clearChildren();
			refreshJobInfo(info);
			currentTaskName = taskName;
			if (listener != null) {
				listener.setTaskName(taskName);
			}
		}

		public void subTask(String name) {
			if (name == null) {
				return;
			}
			JobInfo info = getJobInfo(job);
			info.clearChildren();
			info.addSubTask(name);
			refreshJobInfo(info);
			if (listener != null) {
				listener.subTask(name);
			}
		}

		public void worked(int work) {
			internalWorked(work);
		}

		public void clearBlocked() {
			JobInfo info = getJobInfo(job);
			info.setBlockedStatus(null);
			refreshJobInfo(info);
			if (listener != null) {
				listener.clearBlocked();
			}
		}

		public void setBlocked(IStatus reason) {
			JobInfo info = getJobInfo(job);
			info.setBlockedStatus(reason);
			refreshJobInfo(info);
			if (listener != null) {
				listener.setBlocked(reason);
			}
		}
	}

	ProgressManager() {

		setUpImages();

		changeListener = createChangeListener();


		Job.getJobManager().setProgressProvider(this);
		Job.getJobManager().addJobChangeListener(this.changeListener);
	}

	private void setUpImages() {
		URL iconsRoot = ProgressManagerUtil.getIconsRoot();
		try {
			setUpImage(iconsRoot, SLEEPING_JOB, SLEEPING_JOB_KEY);
			setUpImage(iconsRoot, WAITING_JOB, WAITING_JOB_KEY);
			setUpImage(iconsRoot, BLOCKED_JOB, BLOCKED_JOB_KEY);

			ImageDescriptor errorImage = ImageDescriptor
					.createFromURL(new URL(iconsRoot, ERROR_JOB));
			JFaceResources.getImageRegistry().put(ERROR_JOB_KEY, errorImage);

		} catch (MalformedURLException e) {
			ProgressManagerUtil.logException(e);
		}
	}

	private IJobChangeListener createChangeListener() {
		return new JobChangeAdapter() {

			public void aboutToRun(IJobChangeEvent event) {
				JobInfo info = getJobInfo(event.getJob());
				refreshJobInfo(info);
				Iterator startListeners = busyListenersForJob(event.getJob())
						.iterator();
				while (startListeners.hasNext()) {
					IJobBusyListener next = (IJobBusyListener) startListeners
							.next();
					next.incrementBusy(event.getJob());
				}
			}

			public void done(IJobChangeEvent event) {
				if (!PlatformUI.isWorkbenchRunning()) {
					return;
				}
				Iterator startListeners = busyListenersForJob(event.getJob())
						.iterator();
				while (startListeners.hasNext()) {
					IJobBusyListener next = (IJobBusyListener) startListeners
							.next();
					next.decrementBusy(event.getJob());
				}

				final JobInfo info = getJobInfo(event.getJob());
				removeJobInfo(info);
			}

			public void scheduled(IJobChangeEvent event) {
				updateFor(event);
				if (event.getJob().isUser()) {
					boolean noDialog = shouldRunInBackground();
					if (!noDialog) {
						final IJobChangeEvent finalEvent = event;
						WorkbenchJob showJob = new WorkbenchJob(
								ProgressMessages.ProgressManager_showInDialogName) {
							public IStatus runInUIThread(
									IProgressMonitor monitor) {
								showInDialog(null, finalEvent.getJob());
								return Status.OK_STATUS;
							}
						};
						showJob.setSystem(true);
						showJob.schedule();
						return;
					}
				}
			}

			private void updateFor(IJobChangeEvent event) {
				if (isInfrastructureJob(event.getJob())) {
					return;
				}
				if (jobs.containsKey(event.getJob())) {
					refreshJobInfo(getJobInfo(event.getJob()));
				} else {
					addJobInfo(new JobInfo(event.getJob()));
				}
			}

			public void awake(IJobChangeEvent event) {
				updateFor(event);
			}

			public void sleeping(IJobChangeEvent event) {

				if (jobs.containsKey(event.getJob()))// Are we showing this?
					sleepJobInfo(getJobInfo(event.getJob()));
			}
		};
	}

	protected void sleepJobInfo(JobInfo info) {
		if (isInfrastructureJob(info.getJob()))
			return;

		GroupInfo group = info.getGroupInfo();
		if (group != null) {
			sleepGroup(group,info);
		}

		Object[] listenersArray = listeners.getListeners();
		for (int i = 0; i < listenersArray.length; i++) {
			IJobProgressManagerListener listener = (IJobProgressManagerListener) listenersArray[i];
			if (isNeverDisplaying(info.getJob(), listener.showsDebug()))
				continue;
			if (listener.showsDebug())
				listener.refreshJobInfo(info);
			else
				listener.removeJob(info);

		}
	}

	public IProgressMonitor getDefaultMonitor() {
		Display display;
		if (PlatformUI.isWorkbenchRunning() && !PlatformUI.isWorkbenchStarting()) {
			display = ExternalServices.getDisplay();
			if (!display.isDisposed() && (display.getThread() == Thread.currentThread())) {
				return new EventLoopProgressMonitor(new NullProgressMonitor());
			}
		}
		return super.getDefaultMonitor();
	}

	private void sleepGroup(GroupInfo group, JobInfo info) {
		Object[] listenersArray = listeners.getListeners();
		for (int i = 0; i < listenersArray.length; i++) {
			
			IJobProgressManagerListener listener = (IJobProgressManagerListener) listenersArray[i];
			if (isNeverDisplaying(info.getJob(), listener.showsDebug()))
				continue;
	
			if (listener.showsDebug() || group.isActive())
				listener.refreshGroup(group);
			else
				listener.removeGroup(group);
		}
	}

	private void setUpImage(URL iconsRoot, String fileName, String key)
			throws MalformedURLException {
		JFaceResources.getImageRegistry().put(key,
				ImageDescriptor.createFromURL(new URL(iconsRoot, fileName)));
	}

	public IProgressMonitor createMonitor(Job job) {
		return progressFor(job);
	}

	public JobMonitor progressFor(Job job) {

		synchronized (runnableMonitors) {
			JobMonitor monitor = (JobMonitor) runnableMonitors.get(job);
			if (monitor == null) {
				monitor = new JobMonitor(job);
				runnableMonitors.put(job, monitor);
			}
			
			return monitor;
		}

	}

	void addListener(IJobProgressManagerListener listener) {
		listeners.add(listener);
	}

	void removeListener(IJobProgressManagerListener listener) {
		listeners.remove(listener);
	}

	JobInfo getJobInfo(Job job) {
		JobInfo info = internalGetJobInfo(job);
		if (info == null) {
			info = new JobInfo(job);
			jobs.put(job, info);
		}
		return info;
	}

	JobInfo internalGetJobInfo(Job job) {
		return (JobInfo) jobs.get(job);
	}

	public void refreshJobInfo(JobInfo info) {
		GroupInfo group = info.getGroupInfo();
		if (group != null) {
			refreshGroup(group);
		}

		Object[] listenersArray = listeners.getListeners();
		for (int i = 0; i < listenersArray.length; i++) {
			IJobProgressManagerListener listener = (IJobProgressManagerListener) listenersArray[i];
			if (!isCurrentDisplaying(info.getJob(), listener.showsDebug())) {
				listener.refreshJobInfo(info);
			}
		}
	}

	public void refreshGroup(GroupInfo info) {

		Object[] listenersArray = listeners.getListeners();
		for (int i = 0; i < listenersArray.length; i++) {
			((IJobProgressManagerListener)listenersArray[i]).refreshGroup(info);
		}
	}

	public void refreshAll() {

		pruneStaleJobs();
		Object[] listenersArray = listeners.getListeners();
		for (int i = 0; i < listenersArray.length; i++) {
			((IJobProgressManagerListener)listenersArray[i]).refreshAll();
		}

	}

	public void removeJobInfo(JobInfo info) {

		Job job = info.getJob();
		jobs.remove(job);
		runnableMonitors.remove(job);

		Object[] listenersArray = listeners.getListeners();
		for (int i = 0; i < listenersArray.length; i++) {
			IJobProgressManagerListener listener = (IJobProgressManagerListener) listenersArray[i];
			if (!isCurrentDisplaying(info.getJob(), listener.showsDebug())) {
				listener.removeJob(info);
			}
		}
	}

	public void removeGroup(GroupInfo group) {

		Object[] listenersArray = listeners.getListeners();
		for (int i = 0; i < listenersArray.length; i++) {
			((IJobProgressManagerListener)listenersArray[i]).removeGroup(group);
		}
	}

	public void addJobInfo(JobInfo info) {
		GroupInfo group = info.getGroupInfo();
		if (group != null) {
			refreshGroup(group);
		}

		jobs.put(info.getJob(), info);
		Object[] listenersArray = listeners.getListeners();
		for (int i = 0; i < listenersArray.length; i++) {
			IJobProgressManagerListener listener = (IJobProgressManagerListener) listenersArray[i];
			if (!isCurrentDisplaying(info.getJob(), listener.showsDebug())) {
				listener.addJob(info);
			}
		}
	}

	boolean isCurrentDisplaying(Job job, boolean debug) {
		return isNeverDisplaying(job, debug) || job.getState() == Job.SLEEPING;
	}

	boolean isNeverDisplaying(Job job, boolean debug) {
		if (isInfrastructureJob(job)) {
			return true;
		}
		if (debug)
			return false;

		return job.isSystem();
	}

	private boolean isInfrastructureJob(Job job) {
		if (Policy.DEBUG_SHOW_ALL_JOBS)
			return false;
		return job.getProperty(ProgressManagerUtil.INFRASTRUCTURE_PROPERTY) != null;
	}

	public JobInfo[] getJobInfos(boolean debug) {
		synchronized (jobs) {
			Iterator iterator = jobs.keySet().iterator();
			Collection result = new ArrayList();
			while (iterator.hasNext()) {
				Job next = (Job) iterator.next();
				if (!isCurrentDisplaying(next, debug)) {
					result.add(jobs.get(next));
				}
			}
			JobInfo[] infos = new JobInfo[result.size()];
			result.toArray(infos);
			return infos;
		}
	}

	public JobTreeElement[] getRootElements(boolean debug) {
		synchronized (jobs) {
			Iterator iterator = jobs.keySet().iterator();
			Collection result = new HashSet();
			while (iterator.hasNext()) {
				Job next = (Job) iterator.next();
				if (!isCurrentDisplaying(next, debug)) {
					JobInfo jobInfo = (JobInfo) jobs.get(next);
					GroupInfo group = jobInfo.getGroupInfo();
					if (group == null) {
						result.add(jobInfo);
					} else {
						result.add(group);
					}
				}
			}
			JobTreeElement[] infos = new JobTreeElement[result.size()];
			result.toArray(infos);
			return infos;
		}
	}

	public boolean hasJobInfos() {
		synchronized (jobs) {
			Iterator iterator = jobs.keySet().iterator();
			while (iterator.hasNext()) {
				return true;
			}
			return false;
		}
	}

	Image getImage(ImageData source) {
		ImageData mask = source.getTransparencyMask();
		return new Image(null, source, mask);
	}

	ImageData[] getImageData(URL fileSystemPath, ImageLoader loader) {
		try {
			InputStream stream = fileSystemPath.openStream();
			ImageData[] result = loader.load(stream);
			stream.close();
			return result;
		} catch (FileNotFoundException exception) {
			ProgressManagerUtil.logException(exception);
			return null;
		} catch (IOException exception) {
			ProgressManagerUtil.logException(exception);
			return null;
		}
	}

	public void busyCursorWhile(final IRunnableWithProgress runnable)
			throws InvocationTargetException, InterruptedException {
		final ProgressMonitorJobsDialog dialog = new ProgressMonitorJobsDialog(
				ProgressManagerUtil.getDefaultParent());
		dialog.setOpenOnRun(false);
		final InvocationTargetException[] invokes = new InvocationTargetException[1];
		final InterruptedException[] interrupt = new InterruptedException[1];
		Runnable dialogWaitRunnable = new Runnable() {
			public void run() {
				try {
					dialog.setOpenOnRun(false);
					setUserInterfaceActive(false);
					dialog.run(true, true, runnable);
				} catch (InvocationTargetException e) {
					invokes[0] = e;
				} catch (InterruptedException e) {
					interrupt[0] = e;
				} finally {
					setUserInterfaceActive(true);
				}
			}
		};
		busyCursorWhile(dialogWaitRunnable, dialog);
		if (invokes[0] != null) {
			throw invokes[0];
		}
		if (interrupt[0] != null) {
			throw interrupt[0];
		}
	}

	private void busyCursorWhile(Runnable dialogWaitRunnable,
			ProgressMonitorJobsDialog dialog) {
		scheduleProgressMonitorJob(dialog);
		final Display display = ExternalServices.getDisplay();
		if (display == null) {
			return;
		}
		BusyIndicator.showWhile(display, dialogWaitRunnable);
	}

	private void scheduleProgressMonitorJob(
			final ProgressMonitorJobsDialog dialog) {

		final WorkbenchJob updateJob = new WorkbenchJob(
				ProgressMessages.ProgressManager_openJobName) {
			public IStatus runInUIThread(IProgressMonitor monitor) {
				setUserInterfaceActive(true);
				if (ProgressManagerUtil.safeToOpen(dialog, null)) {
					dialog.open();
				}
				return Status.OK_STATUS;
			}
		};
		updateJob.setSystem(true);
		updateJob.schedule(getLongOperationTime());

	}

	private void shutdown() {
		listeners.clear();
		Job.getJobManager().setProgressProvider(null);
		Job.getJobManager().removeJobChangeListener(this.changeListener);
	}

	public IProgressMonitor createProgressGroup() {
		return new GroupInfo();
	}

	public IProgressMonitor createMonitor(Job job, IProgressMonitor group,
			int ticks) {
		JobMonitor monitor = progressFor(job);
		if (group instanceof GroupInfo) {
			GroupInfo groupInfo = (GroupInfo) group;
			JobInfo jobInfo = getJobInfo(job);
			jobInfo.setGroupInfo(groupInfo);
			jobInfo.setTicks(ticks);
			groupInfo.addJobInfo(jobInfo);
		}
		return monitor;
	}

	void addListenerToFamily(Object family, IJobBusyListener listener) {
		synchronized (familyListeners) {
			Collection currentListeners = (Collection) familyListeners.get(family);
			if (currentListeners == null) {
				currentListeners = new HashSet();
				familyListeners.put(family, currentListeners);
			}
			currentListeners.add(listener);
		}
	}

	void removeListener(IJobBusyListener listener) {
		synchronized (familyListeners) {
			Iterator families = familyListeners.keySet().iterator();
			while (families.hasNext()) {
				Object next = families.next();
				Collection currentListeners = (Collection) familyListeners
						.get(next);
				currentListeners.remove(listener);

				if (currentListeners.isEmpty()) {
					families.remove();
				}
			}
		}
	}

	private Collection busyListenersForJob(Job job) {
		if (job.isSystem()) {
			return Collections.EMPTY_LIST;
		}
		synchronized (familyListeners) {

			if (familyListeners.isEmpty()) {
				return Collections.EMPTY_LIST;
			}

			Iterator families = familyListeners.keySet().iterator();
			Collection returnValue = new HashSet();
			while (families.hasNext()) {
				Object next = families.next();
				if (job.belongsTo(next)) {
					Collection currentListeners = (Collection) familyListeners
							.get(next);
					returnValue.addAll(currentListeners);
				}
			}
			return returnValue;
		}
	}

	public void showInDialog(Shell shell, Job job) {
		if (shouldRunInBackground()) {
			return;
		}

		final ProgressMonitorFocusJobDialog dialog = new ProgressMonitorFocusJobDialog(
				shell);
		dialog.show(job, shell);
	}

	public void run(boolean fork, boolean cancelable,
			IRunnableWithProgress runnable) throws InvocationTargetException,
			InterruptedException {
		if (fork == false || cancelable == false) {
			final ProgressMonitorJobsDialog dialog = new ProgressMonitorJobsDialog(
					null);
			dialog.run(fork, cancelable, runnable);
			return;
		}

		busyCursorWhile(runnable);
	}

	public void runInUI(final IRunnableContext context,
			final IRunnableWithProgress runnable, final ISchedulingRule rule)
			throws InvocationTargetException, InterruptedException {
		final RunnableWithStatus runnableWithStatus = new RunnableWithStatus(
				context,
				runnable, rule);
		ExternalServices.getUiSynchronize().syncExec(new Runnable() {
			public void run() {
				BusyIndicator.showWhile(ExternalServices.getDisplay(), runnableWithStatus);
			}

		});

		IStatus status = runnableWithStatus.getStatus();
		if (!status.isOK()) {
			Throwable exception = status.getException();
			if (exception instanceof InvocationTargetException)
				throw (InvocationTargetException) exception;
			else if (exception instanceof InterruptedException)
				throw (InterruptedException) exception;
			else // should be OperationCanceledException
				throw new InterruptedException(exception.getMessage());
		}
	}

	public int getLongOperationTime() {
		return 800;
	}

	public void registerIconForFamily(ImageDescriptor icon, Object family) {
		String key = IMAGE_KEY + String.valueOf(imageKeyTable.size());
		imageKeyTable.put(family, key);
		ImageRegistry registry = JFaceResources.getImageRegistry();

		if (registry.getDescriptor(key) == null) {
			registry.put(key, icon);
		}

	}

	public Image getIconFor(Job job) {
		Enumeration families = imageKeyTable.keys();
		while (families.hasMoreElements()) {
			Object next = families.nextElement();
			if (job.belongsTo(next)) {
				return JFaceResources.getImageRegistry().get(
						(String) imageKeyTable.get(next));
			}
		}
		return null;
	}

	private void setUserInterfaceActive(boolean active) {
		Shell[] shells = ExternalServices.getDisplay().getShells();
		if (active) {
			for (int i = 0; i < shells.length; i++) {
				if (!shells[i].isDisposed()) {
					shells[i].setEnabled(active);
				}
			}
		} else {
			for (int i = shells.length - 1; i >= 0; i--) {
				if (!shells[i].isDisposed()) {
					shells[i].setEnabled(active);
				}
			}
		}
	}

	private boolean pruneStaleJobs() {
		Object[] jobsToCheck = jobs.keySet().toArray();
		boolean pruned = false;
		for (int i = 0; i < jobsToCheck.length; i++) {
			Job job = (Job) jobsToCheck[i];
			if (checkForStaleness(job)) {
				pruned = true;
			}
		}

		return pruned;
	}

	boolean checkForStaleness(Job job) {
		if (job.getState() == Job.NONE) {
			removeJobInfo(getJobInfo(job));
			return true;
		}
		return false;
	}

	private boolean shouldRunInBackground() {
		return false;
	}

	public void setShowSystemJobs(boolean showSystem) {
		ProgressViewUpdater updater = ProgressViewUpdater.getSingleton();
		updater.debug = showSystem;
		updater.refreshAll();

	}

	private class RunnableWithStatus implements Runnable {

		IStatus status = Status.OK_STATUS;
		private final IRunnableContext context;
		private final IRunnableWithProgress runnable;
		private final ISchedulingRule rule;

		public RunnableWithStatus(IRunnableContext context,
				IRunnableWithProgress runnable, ISchedulingRule rule) {
			this.context = context;
			this.runnable = runnable;
			this.rule = rule;
		}

		public void run() {
			IJobManager manager = Job.getJobManager();
			try {
				manager.beginRule(rule, getEventLoopMonitor());
				context.run(false, false, runnable);
			} catch (InvocationTargetException e) {
				status = new Status(IStatus.ERROR, IProgressConstants.PLUGIN_ID, e
						.getMessage(), e);
			} catch (InterruptedException e) {
				status = new Status(IStatus.ERROR, IProgressConstants.PLUGIN_ID, e
						.getMessage(), e);
			} catch (OperationCanceledException e) {
				status = new Status(IStatus.ERROR, IProgressConstants.PLUGIN_ID, e
						.getMessage(), e);
			} finally {
				manager.endRule(rule);
			}
		}

		private IProgressMonitor getEventLoopMonitor() {

			if (PlatformUI.isWorkbenchStarting())
				return new NullProgressMonitor();

			return new EventLoopProgressMonitor(new NullProgressMonitor()) {

				public void setBlocked(IStatus reason) {

					Dialog.getBlockedHandler().showBlocked(
							ProgressManagerUtil.getDefaultParent(), this,
							reason, getTaskName());
				}
			};
		}

		public IStatus getStatus() {
			return status;
		}

	}
}

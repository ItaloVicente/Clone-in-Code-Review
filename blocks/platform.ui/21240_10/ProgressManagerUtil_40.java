package org.eclipse.e4.ui.progress.internal;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IProgressMonitorWithBlocking;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.ListenerList;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.IJobChangeListener;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;
import org.eclipse.core.runtime.jobs.ProgressProvider;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.progress.IProgressConstants;
import org.eclipse.e4.ui.progress.IProgressService;
import org.eclipse.e4.ui.progress.WorkbenchJob;
import org.eclipse.e4.ui.progress.internal.legacy.EventLoopProgressMonitor;
import org.eclipse.e4.ui.progress.internal.legacy.PlatformUI;
import org.eclipse.e4.ui.progress.internal.legacy.Policy;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.widgets.Display;

public class ProgressManager extends ProgressProvider {
	public static final QualifiedName PROPERTY_IN_DIALOG = IProgressConstants.PROPERTY_IN_DIALOG;

	private static final String ERROR_JOB = "errorstate.png"; //$NON-NLS-1$

	static final String ERROR_JOB_KEY = "ERROR_JOB"; //$NON-NLS-1$

	private static ProgressManager singleton;

	final private Map jobs = Collections.synchronizedMap(new HashMap());
	
	final Map runnableMonitors = Collections.synchronizedMap(new HashMap());

	final private Map familyListeners = Collections
			.synchronizedMap(new HashMap());

	private ListenerList listeners = new ListenerList();
	
	IJobChangeListener changeListener;

	static final String PROGRESS_VIEW_NAME = "org.eclipse.e4.ui.progress.ProgressView"; //$NON-NLS-1$
	
	static final String PROGRESS_FOLDER = "progress/"; //$NON-NLS-1$

	private static final String SLEEPING_JOB = "sleeping.png"; //$NON-NLS-1$

	private static final String WAITING_JOB = "waiting.png"; //$NON-NLS-1$

	private static final String BLOCKED_JOB = "lockedstate.png"; //$NON-NLS-1$

	public static final String SLEEPING_JOB_KEY = "SLEEPING_JOB"; //$NON-NLS-1$

	public static final String WAITING_JOB_KEY = "WAITING_JOB"; //$NON-NLS-1$

	public static final String BLOCKED_JOB_KEY = "LOCKED_JOB"; //$NON-NLS-1$

	

	private static final String IMAGE_KEY = "org.eclipse.ui.progress.images"; //$NON-NLS-1$
	
	@Inject
	@Optional
	IProgressService progressService;
	
	@Inject
	JobInfoFactory jobInfoFactory;
	
	@Optional
	@Inject
	FinishedJobs finishedJobs;

	public static void shutdownProgressManager() {
		if (singleton == null) {
			return;
		}
		singleton.shutdown();
	}

	
	@PostConstruct
	protected void init(WorkbenchDialogBlockedHandler dialogBlockedHandler) {
		Dialog.setBlockedHandler(dialogBlockedHandler);
		
		setUpImages();
		
		changeListener = createChangeListener();
		
		Job.getJobManager().setProgressProvider(this);
		Job.getJobManager().addJobChangeListener(this.changeListener);
	}

	private void setUpImages() {
		ImageTools imageTools = ImageTools.getInstance();
		
		imageTools.putIntoRegistry(SLEEPING_JOB_KEY, PROGRESS_FOLDER
		        + SLEEPING_JOB);
		imageTools.putIntoRegistry(WAITING_JOB_KEY, PROGRESS_FOLDER
		        + WAITING_JOB);
		imageTools.putIntoRegistry(BLOCKED_JOB_KEY, PROGRESS_FOLDER
		        + BLOCKED_JOB);
		imageTools.putIntoRegistry(ERROR_JOB_KEY, PROGRESS_FOLDER + ERROR_JOB);
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
	

	public IProgressMonitor createProgressGroup() {
		return new GroupInfo(this, finishedJobs);
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

	JobInfo getJobInfo(Job job) {
		JobInfo info = internalGetJobInfo(job);
		if (info == null) {
			info = jobInfoFactory.getJobInfo(job);
			jobs.put(job, info);
		}
		return info;
	}

	JobInfo internalGetJobInfo(Job job) {
		return (JobInfo) jobs.get(job);
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
								progressService.showInDialog(null, finalEvent.getJob());
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
					addJobInfo(jobInfoFactory.getJobInfo(event.getJob()));
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
			display = getDisplay();
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

	

	void addListener(IJobProgressManagerListener listener) {
		listeners.add(listener);
	}

	void removeListener(IJobProgressManagerListener listener) {
		listeners.remove(listener);
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

	private void shutdown() {
		listeners.clear();
		Job.getJobManager().setProgressProvider(null);
		Job.getJobManager().removeJobChangeListener(this.changeListener);
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

	protected boolean shouldRunInBackground() {
		return Preferences.getBoolean(IProgressConstants.RUN_IN_BACKGROUND);
	}
	
	protected Display getDisplay() {
		return Services.getInstance().getDisplay();
	}
	
}

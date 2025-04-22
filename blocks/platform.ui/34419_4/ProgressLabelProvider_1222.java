
package org.eclipse.ui.internal.progress;

import com.ibm.icu.text.DateFormat;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.NotEnabledException;
import org.eclipse.core.commands.NotHandledException;
import org.eclipse.core.commands.ParameterizedCommand;
import org.eclipse.core.commands.common.NotDefinedException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.resource.LocalResourceManager;
import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jface.util.Util;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.IHandlerService;
import org.eclipse.ui.internal.WorkbenchImages;
import org.eclipse.ui.progress.IProgressConstants;
import org.eclipse.ui.progress.IProgressConstants2;
import org.eclipse.ui.statushandlers.StatusManager;

public class ProgressInfoItem extends Composite {

	static String STOP_IMAGE_KEY = "org.eclipse.ui.internal.progress.PROGRESS_STOP"; //$NON-NLS-1$

	static String DISABLED_STOP_IMAGE_KEY = "org.eclipse.ui.internal.progress.DISABLED_PROGRESS_STOP"; //$NON-NLS-1$

	static String CLEAR_FINISHED_JOB_KEY = "org.eclipse.ui.internal.progress.CLEAR_FINISHED_JOB"; //$NON-NLS-1$

	static String DISABLED_CLEAR_FINISHED_JOB_KEY = "org.eclipse.ui.internal.progress.DISABLED_CLEAR_FINISHED_JOB"; //$NON-NLS-1$

	static String DEFAULT_JOB_KEY = "org.eclipse.ui.internal.progress.PROGRESS_DEFAULT"; //$NON-NLS-1$

	static String DARK_COLOR_KEY = "org.eclipse.ui.internal.progress.PROGRESS_DARK_COLOR"; //$NON-NLS-1$

	JobTreeElement info;

	Label progressLabel;

	ToolBar actionBar;

	ToolItem actionButton;

	List taskEntries = new ArrayList(0);

	private ProgressBar progressBar;

	private Label jobImageLabel;

	static final int MAX_PROGRESS_HEIGHT = 12;

	static final int MIN_ICON_SIZE = 16;

	private static final String TEXT_KEY = "Text"; //$NON-NLS-1$

	private static final String TRIGGER_KEY = "Trigger";//$NON-NLS-1$

	interface IndexListener {
		public void selectPrevious();

		public void selectNext();

		public void select();
	}

	IndexListener indexListener;

	private int currentIndex;

	private boolean selected;

	private MouseAdapter mouseListener;

	private boolean isShowing = true;

	private ResourceManager resourceManager;

	private Link link;

	static {
		JFaceResources
				.getImageRegistry()
				.put(
						STOP_IMAGE_KEY,
						WorkbenchImages
								.getWorkbenchImageDescriptor("elcl16/progress_stop.png"));//$NON-NLS-1$

		JFaceResources
				.getImageRegistry()
				.put(
						DISABLED_STOP_IMAGE_KEY,
						WorkbenchImages
								.getWorkbenchImageDescriptor("dlcl16/progress_stop.png"));//$NON-NLS-1$

		JFaceResources
				.getImageRegistry()
				.put(
						DEFAULT_JOB_KEY,
						WorkbenchImages
								.getWorkbenchImageDescriptor("progress/progress_task.png")); //$NON-NLS-1$

		JFaceResources
				.getImageRegistry()
				.put(
						CLEAR_FINISHED_JOB_KEY,
						WorkbenchImages
								.getWorkbenchImageDescriptor("elcl16/progress_rem.png")); //$NON-NLS-1$

		JFaceResources
				.getImageRegistry()
				.put(
						DISABLED_CLEAR_FINISHED_JOB_KEY,
						WorkbenchImages
								.getWorkbenchImageDescriptor("dlcl16/progress_rem.png")); //$NON-NLS-1$

		int shift = Util.isMac() ? -25 : -10;

		Color lightColor = PlatformUI.getWorkbench().getDisplay()
				.getSystemColor(SWT.COLOR_LIST_BACKGROUND);

		RGB darkRGB = new RGB(Math.max(0, lightColor.getRed() + shift), Math
				.max(0, lightColor.getGreen() + shift), Math.max(0, lightColor
				.getBlue()
				+ shift));
		JFaceResources.getColorRegistry().put(DARK_COLOR_KEY, darkRGB);
	}

	public ProgressInfoItem(Composite parent, int style,
			JobTreeElement progressInfo) {
		super(parent, style);
		info = progressInfo;
		setData(info);
		setLayoutData(new GridData(SWT.FILL, SWT.NONE, true, false));
		createChildren();
	}

	protected void createChildren() {

		FormLayout layout = new FormLayout();
		setLayout(layout);

		jobImageLabel = new Label(this, SWT.NONE);
		Image infoImage = getInfoImage();
		jobImageLabel.setImage(infoImage);
		FormData imageData = new FormData();
		if (infoImage != null) {
			imageData.top = new FormAttachment(50,
					-infoImage.getBounds().height / 2);
		} else {
			imageData.top = new FormAttachment(0,
					IDialogConstants.VERTICAL_SPACING);
		}
		imageData.left = new FormAttachment(0,
				IDialogConstants.HORIZONTAL_SPACING / 2);
		jobImageLabel.setLayoutData(imageData);

		progressLabel = new Label(this, SWT.NONE);
		setMainText();

		actionBar = new ToolBar(this, SWT.FLAT);
		actionBar.setCursor(getDisplay().getSystemCursor(SWT.CURSOR_ARROW));


		actionButton = new ToolItem(actionBar, SWT.NONE);
		actionButton
				.setToolTipText(ProgressMessages.NewProgressView_CancelJobToolTip);
		actionButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				actionButton.setEnabled(false);
				cancelOrRemove();
			}
		});
		actionBar.addListener(SWT.Traverse, new Listener() {
			@Override
			public void handleEvent(Event event) {
				if (indexListener == null) {
					return;
				}
				int detail = event.detail;
				if (detail == SWT.TRAVERSE_ARROW_NEXT) {
					indexListener.selectNext();
				}
				if (detail == SWT.TRAVERSE_ARROW_PREVIOUS) {
					indexListener.selectPrevious();
				}

			}
		});
		updateToolBarValues();

		FormData progressData = new FormData();
		progressData.top = new FormAttachment(0,
				IDialogConstants.VERTICAL_SPACING);
		progressData.left = new FormAttachment(jobImageLabel,
				IDialogConstants.HORIZONTAL_SPACING / 2);
		progressData.right = new FormAttachment(actionBar,
				IDialogConstants.HORIZONTAL_SPACING * -1);
		progressLabel.setLayoutData(progressData);

		mouseListener = new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				if (indexListener != null) {
					indexListener.select();
				}
			}
		};
		addMouseListener(mouseListener);
		jobImageLabel.addMouseListener(mouseListener);
		progressLabel.addMouseListener(mouseListener);

		setLayoutsForNoProgress();

		refresh();
	}

	private void setMainText() {
		progressLabel
				.setText(Dialog.shortenText(getMainTitle(), progressLabel));
	}

	private void setLayoutsForNoProgress() {

		FormData buttonData = new FormData();
		buttonData.top = new FormAttachment(progressLabel, 0, SWT.TOP);
		buttonData.right = new FormAttachment(100,
				IDialogConstants.HORIZONTAL_SPACING * -1);

		actionBar.setLayoutData(buttonData);
		if (taskEntries.size() > 0) {
			FormData linkData = new FormData();
			linkData.top = new FormAttachment(progressLabel,
					IDialogConstants.VERTICAL_SPACING);
			linkData.left = new FormAttachment(progressLabel, 0, SWT.LEFT);
			linkData.right = new FormAttachment(actionBar, 0, SWT.LEFT);
			((Link) taskEntries.get(0)).setLayoutData(linkData);

		}
	}

	protected void cancelOrRemove() {

		if (FinishedJobs.getInstance().isKept(info) && isCompleted()) {
			FinishedJobs.getInstance().remove(info);
		} else {
			info.cancel();
		}

	}

	private Image getInfoImage() {

		if (!info.isJobInfo()) {
			return JFaceResources.getImage(DEFAULT_JOB_KEY);
		}

		JobInfo jobInfo = (JobInfo) info;

		ImageDescriptor descriptor = null;
		Object property = jobInfo.getJob().getProperty(
				IProgressConstants.ICON_PROPERTY);

		if (property instanceof ImageDescriptor) {
			descriptor = (ImageDescriptor) property;
		} else if (property instanceof URL) {
			descriptor = ImageDescriptor.createFromURL((URL) property);
		}

		Image image = null;
		if (descriptor == null) {
			image = ProgressManager.getInstance().getIconFor(jobInfo.getJob());
		} else {
			image = getResourceManager().createImageWithDefault(descriptor);
		}

		if (image == null)
			image = jobInfo.getDisplayImage();

		return image;
	}

	private ResourceManager getResourceManager() {
		if (resourceManager == null)
			resourceManager = new LocalResourceManager(JFaceResources
					.getResources());
		return resourceManager;
	}

	private String getMainTitle() {
		if (info.isJobInfo()) {
			return getJobNameAndStatus((JobInfo) info);
		}
		if (info.hasChildren()) {
			return ((GroupInfo) info).getTaskName();
		}
		return info.getDisplayString();

	}

	public String getJobNameAndStatus(JobInfo jobInfo) {

		Job job = jobInfo.getJob();

		String name = job.getName();

		if (job.isSystem()) {
			name = NLS.bind(ProgressMessages.JobInfo_System, name);
		}

		if (jobInfo.isCanceled()) {
			if (job.getState() == Job.RUNNING)
				return NLS
						.bind(ProgressMessages.JobInfo_Cancel_Requested, name);
			return NLS.bind(ProgressMessages.JobInfo_Cancelled, name);
		}

		if (jobInfo.isBlocked()) {
			IStatus blockedStatus = jobInfo.getBlockedStatus();
			return NLS.bind(ProgressMessages.JobInfo_Blocked, name,
					blockedStatus.getMessage());
		}

		switch (job.getState()) {
		case Job.RUNNING:
			return name;
		case Job.SLEEPING: {
			return NLS.bind(ProgressMessages.JobInfo_Sleeping, name);

		}
		case Job.NONE: // Only happens for kept jobs
			return getJobInfoFinishedString(job, true);
		default:
			return NLS.bind(ProgressMessages.JobInfo_Waiting, name);
		}
	}

	String getJobInfoFinishedString(Job job, boolean withTime) {
		String time = null;
		if (withTime) {
			time = getTimeString();
		}
		if (time != null) {
			return NLS.bind(ProgressMessages.JobInfo_FinishedAt, job.getName(),
					time);
		}
		return NLS.bind(ProgressMessages.JobInfo_Finished, job.getName());
	}

	private String getTimeString() {
		Date date = FinishedJobs.getInstance().getFinishDate(info);
		if (date != null) {
			return DateFormat.getTimeInstance(DateFormat.SHORT).format(date);
		}
		return null;
	}

	void refresh() {

		if (isDisposed() || !isShowing)
			return;

		jobImageLabel.setImage(getInfoImage());
		int percentDone = getPercentDone();
		ProgressBar currentProgressBar = progressBar;

		JobInfo[] infos = getJobInfos();
		if (isRunning()) {
			if (progressBar == null) {
				if (percentDone == IProgressMonitor.UNKNOWN) {
					for (int i = 0; i < infos.length; i++) {
						if (infos[i].hasTaskInfo()
								&& infos[i].getTaskInfo().totalWork == IProgressMonitor.UNKNOWN) {
							createProgressBar(SWT.INDETERMINATE);
							break;
						}
					}
				} else {
					createProgressBar(SWT.NONE);
					progressBar.setMinimum(0);
					progressBar.setMaximum(100);
				}
			}

			if (percentDone >= 0 && percentDone <= 100
					&& percentDone != progressBar.getSelection()) {
				progressBar.setSelection(percentDone);
			}
		}

		else if (isCompleted()) {

			if (progressBar != null) {
				progressBar.dispose();
				progressBar = null;
			}
			setLayoutsForNoProgress();

		}

		for (int i = 0; i < infos.length; i++) {
			JobInfo jobInfo = infos[i];
			TaskInfo taskInfo = jobInfo.getTaskInfo();

			if (taskInfo != null) {

				String taskString = taskInfo.getTaskName();
				String subTaskString = null;
				Object[] jobChildren = jobInfo.getChildren();
				if (jobChildren.length > 0) {
					subTaskString = ((JobTreeElement) jobChildren[0])
							.getDisplayString();
				}

				if (subTaskString != null) {
					if (taskString == null || taskString.length() == 0) {
						taskString = subTaskString;
					} else {
						taskString = NLS.bind(
								ProgressMessages.JobInfo_DoneNoProgressMessage,
								taskString, subTaskString);
					}
				}
				if (taskString != null) {
					setLinkText(infos[i].getJob(), taskString, i);
				}
			} else {// Check for the finished job state
				Job job = jobInfo.getJob();
				IStatus result = job.getResult();

				if (result == null || result.getMessage().length() == 0
						&& !info.isJobInfo()) {
					setLinkText(job, getJobNameAndStatus(jobInfo), i);
				} else {
					setLinkText(job, result.getMessage(), i);

				}

			}
			setColor(currentIndex);
		}

		if (infos.length < taskEntries.size()) {
			for (int i = infos.length; i < taskEntries.size(); i++) {
				((Link) taskEntries.get(i)).dispose();

			}
			if (infos.length > 1)
				taskEntries = taskEntries.subList(0, infos.length - 1);
			else
				taskEntries.clear();
		}

		updateToolBarValues();
		setMainText();

		if (currentProgressBar != progressBar) {
			getParent().layout(new Control[] { this });
		}
	}

	private boolean isCompleted() {

		JobInfo[] infos = getJobInfos();
		for (int i = 0; i < infos.length; i++) {
			if (infos[i].getJob().getState() != Job.NONE) {
				return false;
			}
		}
		return infos.length > 0;
	}

	public JobInfo[] getJobInfos() {
		if (info.isJobInfo()) {
			return new JobInfo[] { (JobInfo) info };
		}
		Object[] children = info.getChildren();
		JobInfo[] infos = new JobInfo[children.length];
		System.arraycopy(children, 0, infos, 0, children.length);
		return infos;
	}

	private boolean isRunning() {

		JobInfo[] infos = getJobInfos();
		for (int i = 0; i < infos.length; i++) {
			int state = infos[i].getJob().getState();
			if (state == Job.WAITING || state == Job.RUNNING)
				return true;
		}
		return false;
	}

	private int getPercentDone() {
		if (info.isJobInfo()) {
			return ((JobInfo) info).getPercentDone();
		}

		if (info.hasChildren()) {
			Object[] roots = ((GroupInfo) info).getChildren();
			if (roots.length == 1 && roots[0] instanceof JobTreeElement) {
				TaskInfo ti = ((JobInfo) roots[0]).getTaskInfo();
				if (ti != null) {
					return ti.getPercentDone();
				}
			}
			return ((GroupInfo) info).getPercentDone();
		}
		return 0;
	}

	private void updateToolBarValues() {
		if (isCompleted()) {
			actionButton.setImage(JFaceResources
					.getImage(CLEAR_FINISHED_JOB_KEY));
			actionButton.setDisabledImage(JFaceResources
					.getImage(DISABLED_CLEAR_FINISHED_JOB_KEY));
			actionButton
					.setToolTipText(ProgressMessages.NewProgressView_ClearJobToolTip);
		} else {
			actionButton.setImage(JFaceResources.getImage(STOP_IMAGE_KEY));
			actionButton.setDisabledImage(JFaceResources
					.getImage(DISABLED_STOP_IMAGE_KEY));

		}
		JobInfo[] infos = getJobInfos();

		for (int i = 0; i < infos.length; i++) {
			if (infos[i].isCanceled() && !isCompleted()) {
				actionButton.setEnabled(false);
				return;
			}
		}
		actionButton.setEnabled(true);
	}

	void createProgressBar(int style) {

		FormData buttonData = new FormData();
		buttonData.top = new FormAttachment(progressLabel, 0);
		buttonData.right = new FormAttachment(100,
				IDialogConstants.HORIZONTAL_SPACING * -1);

		actionBar.setLayoutData(buttonData);

		progressBar = new ProgressBar(this, SWT.HORIZONTAL | style);
		FormData barData = new FormData();
		barData.top = new FormAttachment(actionBar,
				IDialogConstants.VERTICAL_SPACING, SWT.TOP);
		barData.left = new FormAttachment(progressLabel, 0, SWT.LEFT);
		barData.right = new FormAttachment(actionBar,
				IDialogConstants.HORIZONTAL_SPACING * -1);
		barData.height = MAX_PROGRESS_HEIGHT;
		barData.width = 0;// default is too large
		progressBar.setLayoutData(barData);

		if (taskEntries.size() > 0) {
			FormData linkData = new FormData();
			linkData.top = new FormAttachment(progressBar,
					IDialogConstants.VERTICAL_SPACING);
			linkData.left = new FormAttachment(progressBar, 0, SWT.LEFT);
			linkData.right = new FormAttachment(progressBar, 0, SWT.RIGHT);
			linkData.width = 20;

			((Link) taskEntries.get(0)).setLayoutData(linkData);
		}
	}

	void setLinkText(Job linkJob, String taskString, int index) {

		if (index >= taskEntries.size()) {// Is it new?
			link = new Link(this, SWT.NONE);

			FormData linkData = new FormData();
			if (index == 0 || taskEntries.size() == 0) {
				Control top = progressBar;
				if (top == null) {
					top = progressLabel;
				}
				linkData.top = new FormAttachment(top,
						IDialogConstants.VERTICAL_SPACING);
				linkData.left = new FormAttachment(top, 0, SWT.LEFT);
				linkData.right = new FormAttachment(top, 0, SWT.RIGHT);
				linkData.width = 20;
			} else {
				Link previous = (Link) taskEntries.get(index - 1);
				linkData.top = new FormAttachment(previous,
						IDialogConstants.VERTICAL_SPACING);
				linkData.left = new FormAttachment(previous, 0, SWT.LEFT);
				linkData.right = new FormAttachment(previous, 0, SWT.RIGHT);
				linkData.width = 20;
			}

			link.setLayoutData(linkData);

			link.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					executeTrigger();
				}
			});

			link.addListener(SWT.Resize, new Listener() {
				@Override
				public void handleEvent(Event event) {

					Object text = link.getData(TEXT_KEY);
					if (text == null)
						return;

					updateText((String) text, link);

				}
			});
			taskEntries.add(link);
		} else {
			link = (Link) taskEntries.get(index);
		}

		Object actionProperty = linkJob
				.getProperty(IProgressConstants.ACTION_PROPERTY);
		Object commandProperty = linkJob
				.getProperty(IProgressConstants2.COMMAND_PROPERTY);

		if (actionProperty != null && commandProperty != null) {
			updateTrigger(null, link);
		} else {
			Object property = actionProperty != null ? actionProperty
					: commandProperty;
			updateTrigger(property, link);
		}

		if (link.getData(TRIGGER_KEY) == null
				&& (taskString == null || taskString.equals(getMainTitle()))) {
			taskString = ""; //$NON-NLS-1$
		}
		link.setToolTipText(taskString);
		link.setData(TEXT_KEY, taskString);

		updateText(taskString, link);

	}

	public void executeTrigger() {

		Object data = link.getData(TRIGGER_KEY);
		if (data instanceof IAction) {
			IAction action = (IAction) data;
			if (action.isEnabled())
				action.run();
			updateTrigger(action, link);
		} else if (data instanceof ParameterizedCommand) {
			IWorkbench workbench = PlatformUI
					.getWorkbench();
			IHandlerService handlerService = workbench
					.getService(
							IHandlerService.class);
			IStatus status = Status.OK_STATUS;
			try {
				handlerService
						.executeCommand((ParameterizedCommand) data, null);
			} catch (ExecutionException e) {
				status = new Status(IStatus.ERROR, PlatformUI.PLUGIN_ID, e
						.getMessage(), e);
			} catch (NotDefinedException e) {
				status = new Status(IStatus.ERROR, PlatformUI.PLUGIN_ID, e
						.getMessage(), e);
			} catch (NotEnabledException e) {
				status = new Status(IStatus.WARNING, PlatformUI.PLUGIN_ID, e
						.getMessage(), e);
			} catch (NotHandledException e) {
				status = new Status(IStatus.ERROR, PlatformUI.PLUGIN_ID, e
						.getMessage(), e);
			}

			if (!status.isOK()) {
				StatusManager.getManager().handle(status,
						StatusManager.LOG | StatusManager.SHOW);
			}
		}

		if (link.isDisposed()) {
			return;
		}

		Object text = link.getData(TEXT_KEY);
		if (text == null)
			return;

		updateText((String) text, link);
	}

	private void updateTrigger(Object trigger, Link link) {
		if (link.isDisposed()) {
			return;
		}

		if (trigger instanceof IAction && ((IAction) trigger).isEnabled()) {
			link.setData(TRIGGER_KEY, trigger);
		} else if (trigger instanceof ParameterizedCommand) {
			link.setData(TRIGGER_KEY, trigger);
		} else {
			link.setData(TRIGGER_KEY, null);
		}

	}

	private void updateText(String taskString, Link link) {
		taskString = Dialog.shortenText(taskString, link);

		link.setText(link.getData(TRIGGER_KEY) == null ? taskString : NLS.bind(
				"<a>{0}</a>", taskString));//$NON-NLS-1$
	}

	public void setColor(int i) {
		currentIndex = i;

		if (selected) {
			setAllBackgrounds(getDisplay().getSystemColor(
					SWT.COLOR_LIST_SELECTION));
			setAllForegrounds(getDisplay().getSystemColor(
					SWT.COLOR_LIST_SELECTION_TEXT));
			return;
		}

		if (i % 2 == 0) {
			setAllBackgrounds(JFaceResources.getColorRegistry().get(
					DARK_COLOR_KEY));
		} else {
			setAllBackgrounds(getDisplay().getSystemColor(
					SWT.COLOR_LIST_BACKGROUND));
		}
		setAllForegrounds(getDisplay()
				.getSystemColor(SWT.COLOR_LIST_FOREGROUND));
	}

	private void setAllForegrounds(Color color) {
		setForeground(color);
		progressLabel.setForeground(color);

		Iterator taskEntryIterator = taskEntries.iterator();
		while (taskEntryIterator.hasNext()) {
			((Link) taskEntryIterator.next()).setForeground(color);
		}

	}

	private void setAllBackgrounds(Color color) {
		setBackground(color);
		progressLabel.setBackground(color);
		actionBar.setBackground(color);
		jobImageLabel.setBackground(color);

		Iterator taskEntryIterator = taskEntries.iterator();
		while (taskEntryIterator.hasNext()) {
			((Link) taskEntryIterator.next()).setBackground(color);
		}

	}

	void setButtonFocus() {
		actionBar.setFocus();
	}

	void selectWidgets(boolean select) {
		if (select) {
			setButtonFocus();
		}
		selected = select;
		setColor(currentIndex);
	}

	void setIndexListener(IndexListener indexListener) {
		this.indexListener = indexListener;
	}

	boolean isSelected() {
		return selected;
	}

	void setDisplayed(int top, int bottom) {
		int itemTop = getLocation().y;
		int itemBottom = itemTop + getBounds().height;
		setDisplayed(itemTop <= bottom && itemBottom > top);

	}

	private void setDisplayed(boolean displayed) {
		boolean refresh = !isShowing && displayed;
		isShowing = displayed;
		if (refresh)
			refresh();
	}
	
	@Override
	public void dispose() {
		super.dispose();
		if(resourceManager != null)
			resourceManager.dispose();
	}

	public JobTreeElement getInfo() {
		return info;
	}
}

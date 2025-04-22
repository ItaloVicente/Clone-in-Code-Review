package org.eclipse.e4.ui.progress.internal;

import java.net.URL;

import org.eclipse.core.commands.ParameterizedCommand;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.e4.core.commands.EHandlerService;
import org.eclipse.e4.core.services.statusreporter.StatusReporter;
import org.eclipse.e4.ui.progress.IProgressConstants;
import org.eclipse.e4.ui.progress.internal.legacy.PlatformUI;
import org.eclipse.e4.ui.progress.internal.legacy.StatusAdapter;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.util.Util;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;
import org.eclipse.swt.accessibility.AccessibleAdapter;
import org.eclipse.swt.accessibility.AccessibleEvent;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

public class ProgressAnimationItem extends AnimationItem implements
		FinishedJobs.KeptJobsListener {

	ProgressBar bar;

	MouseListener mouseListener;

	Composite top;

	ToolBar toolbar;

	ToolItem toolButton;

	ProgressRegion progressRegion;

	Image noneImage, okImage, errorImage;

	boolean animationRunning;

	private int flags;
	
	private FinishedJobs finishedJobs;

	ProgressAnimationItem(ProgressRegion region, int flags,
	        AnimationManager animationManager, FinishedJobs finishedJobs) {
		super(animationManager);
		this.flags = flags;
		this.finishedJobs = finishedJobs;
		finishedJobs.addListener(this);

		progressRegion = region;
		mouseListener = new MouseAdapter() {
			public void mouseDoubleClick(MouseEvent e) {
				doAction();
			}
		};
	}

	void doAction() {

		JobTreeElement[] jobTreeElements = finishedJobs.getKeptElements();
		for (int i = jobTreeElements.length - 1; i >= 0; i--) {
			if (jobTreeElements[i] instanceof JobInfo) {
				JobInfo ji = (JobInfo) jobTreeElements[i];
				Job job = ji.getJob();
				if (job != null) {

					IStatus status = job.getResult();
					if (status != null && status.getSeverity() == IStatus.ERROR) {

						getStatusReporter().report(status,
						        StatusReporter.SHOW, new Object[0]);
						removeTopElement(ji);
					}

					execute(ji, job);
				}
			}
		}

		progressRegion.processDoubleClick();
		refresh();
	}

	private void execute(JobInfo ji, Job job) {

		Object prop = job.getProperty(IProgressConstants.ACTION_PROPERTY);
		if (prop instanceof IAction && ((IAction) prop).isEnabled()) {
			IAction action = (IAction) prop;
			action.run();
			removeTopElement(ji);
		}

		prop = job.getProperty(IProgressConstants.COMMAND_PROPERTY);
		if (prop instanceof ParameterizedCommand) {
			ParameterizedCommand command = (ParameterizedCommand) prop;
			Exception exception = null;
			getEHandlerService().executeHandler(command);
			removeTopElement(ji);

			if (exception != null) {
				Status status = new Status(IStatus.ERROR, IProgressConstants.PLUGIN_ID,
						exception.getMessage(), exception);
				getStatusReporter().report(status,
				        StatusReporter.LOG | StatusReporter.SHOW, null);
			}

		}
	}

	private void removeTopElement(JobInfo ji) {
		JobTreeElement topElement = (JobTreeElement) ji.getParent();
		if (topElement == null) {
			topElement = ji;
		}
		finishedJobs.remove(topElement);
	}

	private IAction getAction(Job job) {
		Object property = job.getProperty(IProgressConstants.ACTION_PROPERTY);
		if (property instanceof IAction) {
			return (IAction) property;
		}
		return null;
	}

	private void refresh() {

		if (!PlatformUI.isWorkbenchRunning()) {
			return;
		}

		if (toolbar == null || toolbar.isDisposed()) {
			return;
		}

		JobTreeElement[] jobTreeElements = finishedJobs.getKeptElements();
		for (int i = jobTreeElements.length - 1; i >= 0; i--) {
			if (jobTreeElements[i] instanceof JobInfo) {
				JobInfo ji = (JobInfo) jobTreeElements[i];
				Job job = ji.getJob();
				if (job != null) {
					IStatus status = job.getResult();
					if (status != null && status.getSeverity() == IStatus.ERROR) {
						initButton(errorImage, NLS.bind(
								ProgressMessages.ProgressAnimationItem_error,
								job.getName()));
						return;
					}
					IAction action = getAction(job);
					if (action != null && action.isEnabled()) {
						String tt = action.getToolTipText();
						if (tt == null || tt.trim().length() == 0) {
							tt = NLS.bind(
									ProgressMessages.ProgressAnimationItem_ok,
									job.getName());
						}
						initButton(okImage, tt);
						return;
					}
					initButton(noneImage,
							ProgressMessages.ProgressAnimationItem_tasks);
					return;
				}
			}
		}

		if (animationRunning) {
			initButton(noneImage, ProgressMessages.ProgressAnimationItem_tasks);
			return;
		}

		toolbar.setVisible(false);
	}

	private void initButton(Image im, final String tt) {
		toolButton.setImage(im);
		toolButton.setToolTipText(tt);
    	toolbar.setVisible(true);
		toolbar.getParent().layout(); // must layout
		
    	toolbar.getAccessible().addAccessibleListener(new AccessibleAdapter() {
        	public void getName(AccessibleEvent e) {
        		e.result = tt;
        	}
        });
	}

	protected Control createAnimationItem(Composite parent) {

		if (okImage == null) {
			Display display = parent.getDisplay();
			ImageTools imageTools = ImageTools.getInstance();
			
			noneImage = imageTools.getImage("progress/progress_none.png", display); //$NON-NLS-1$
			okImage = imageTools.getImage("progress/progress_ok.png", display); //$NON-NLS-1$
			errorImage = imageTools.getImage("progress/progress_error.png", display); //$NON-NLS-1$
		}

		top = new Composite(parent, SWT.NULL);
		top.addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent e) {
				finishedJobs.removeListener(
						ProgressAnimationItem.this);
				noneImage.dispose();
				okImage.dispose();
				errorImage.dispose();
			}
		});

		boolean isCarbon = Util.isMac();

		GridLayout gl = new GridLayout();
		if (isHorizontal())
			gl.numColumns = isCarbon ? 3 : 2;
		gl.marginHeight = 0;
		gl.marginWidth = 0;
		if (isHorizontal()) {
			gl.horizontalSpacing = 2;
		} else {
			gl.verticalSpacing = 2;
		}
		top.setLayout(gl);

		bar = new ProgressBar(top, flags | SWT.INDETERMINATE);
		bar.setVisible(false);
		bar.addMouseListener(mouseListener);

		GridData gd;
		int hh = 12;
		if (isHorizontal()) {
			gd = new GridData(SWT.BEGINNING, SWT.CENTER, true, false);
			gd.heightHint = hh;
		} else {
			gd = new GridData(SWT.CENTER, SWT.BEGINNING, false, true);
			gd.widthHint = hh;
		}

		bar.setLayoutData(gd);

		toolbar = new ToolBar(top, SWT.FLAT);
		toolbar.setVisible(false);

		toolButton = new ToolItem(toolbar, SWT.NONE);
		toolButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				doAction();
			}
		});

		if (isCarbon) {
			new Label(top, SWT.NONE).setLayoutData(new GridData(4, 4));
		}

		refresh();

		return top;
	}

	private boolean isHorizontal() {
		return (flags & SWT.HORIZONTAL) != 0;
	}

	public Control getControl() {
		return top;
	}

	void animationDone() {
		super.animationDone();
		animationRunning = false;
		if (bar.isDisposed()) {
			return;
		}
		bar.setVisible(false);
		refresh();
	}

	public boolean animationRunning() {
		return animationRunning;
	}

	void animationStart() {
		super.animationStart();
		animationRunning = true;
		if (bar.isDisposed()) {
			return;
		}
		bar.setVisible(true);
		refresh();
	}

	public void removed(JobTreeElement info) {
		final Display display = Display.getDefault();
		display.asyncExec(new Runnable() {
			public void run() {
				refresh();
			}
		});
	}

	public void finished(final JobTreeElement jte) {
		final Display display = Display.getDefault();
		display.asyncExec(new Runnable() {
			public void run() {
				refresh();
			}
		});
	}
	
	protected StatusReporter getStatusReporter() {
	    return Services.getInstance().getStatusReporter();
    }
	
	protected EHandlerService getEHandlerService() {
		return Services.getInstance().getEHandlerService();
	}

}

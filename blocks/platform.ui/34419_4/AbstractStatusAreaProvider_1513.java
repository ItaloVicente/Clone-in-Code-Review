package org.eclipse.ui.splash;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.e4.ui.css.swt.CSSSWTConstants;
import org.eclipse.jface.dialogs.ProgressIndicator;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.ProgressMonitorPart;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.internal.StartupThreading.StartupRunnable;

public abstract class BasicSplashHandler extends AbstractSplashHandler {

	private static final String SPLASH_PROGRESS_PART_ID = "org-eclipse-ui-splash-progressPart"; //$NON-NLS-1$
	private static final String SPLASH_PROGRESS_INDICATOR_ID = "org-eclipse-ui-splash-progressIndicator"; //$NON-NLS-1$
	private static final String SPLASH_PROGRESS_TEXT_ID = "org-eclipse-ui-splash-progressText"; //$NON-NLS-1$

	class AbsolutePositionProgressMonitorPart extends ProgressMonitorPart {
		public AbsolutePositionProgressMonitorPart(Composite parent) {
			super(parent, null);
			setLayout(null);
			setCSSData();
		}

		private void setCSSData() {
			this.setData(CSSSWTConstants.CSS_ID_KEY, SPLASH_PROGRESS_PART_ID);
			fProgressIndicator.setData(CSSSWTConstants.CSS_ID_KEY, SPLASH_PROGRESS_INDICATOR_ID);
			fLabel.setData(CSSSWTConstants.CSS_ID_KEY, SPLASH_PROGRESS_TEXT_ID);
		}

		public ProgressIndicator getProgressIndicator() {
			return fProgressIndicator;
		}

		public Label getProgressText() {
			return fLabel;
		}

		@Override
		public void beginTask(final String name, final int totalWork) {

			updateUI(new Runnable() {

				@Override
				public void run() {
					if (isDisposed())
						return;
					AbsolutePositionProgressMonitorPart.super.beginTask(name,
							totalWork);
				}
			});

		}

		@Override
		public void done() {

			updateUI(new Runnable() {

				@Override
				public void run() {
					if (isDisposed())
						return;
					AbsolutePositionProgressMonitorPart.super.done();
				}
			});

		}

		@Override
		public void internalWorked(final double work) {

			updateUI(new Runnable() {

				@Override
				public void run() {
					if (isDisposed())
						return;
					AbsolutePositionProgressMonitorPart.super
							.internalWorked(work);
				}
			});

		}
		
		@Override
		public void setFont(final Font font) {

			updateUI(new Runnable() {

				@Override
				public void run() {
					if (isDisposed())
						return;
					AbsolutePositionProgressMonitorPart.super.setFont(font);
				}
			});

		}

		@Override
		protected void updateLabel() {

			updateUI(new Runnable() {

				@Override
				public void run() {
					if (isDisposed())
						return;
					AbsolutePositionProgressMonitorPart.super.updateLabel();
				}
			});

		}
	}

	private Color foreground = null;
	private AbsolutePositionProgressMonitorPart monitor;
	private Rectangle messageRect;
	private Rectangle progressRect;

	@Override
	public IProgressMonitor getBundleProgressMonitor() {
		if (monitor == null) {
			Composite parent = new Composite(getSplash(), Window.getDefaultOrientation());
			Point size = getSplash().getSize();
			parent.setBounds(new Rectangle(0,0,size.x,size.y));
			monitor = new AbsolutePositionProgressMonitorPart(parent);
			monitor.setSize(size);
			if (progressRect != null)
				monitor.getProgressIndicator().setBounds(progressRect);
			else
				monitor.getProgressIndicator().setVisible(false);

			if (messageRect != null)
				monitor.getProgressText().setBounds(messageRect);
			else
				monitor.getProgressText().setVisible(false);

			if (foreground != null)
				monitor.getProgressText().setForeground(foreground);
			monitor.setBackgroundMode(SWT.INHERIT_FORCE);
			monitor.setBackgroundImage(getSplash().getShell()
					.getBackgroundImage());
		}
		return monitor;
	}

	@Override
	public void dispose() {
		if (foreground != null)
			foreground.dispose();
		super.dispose();
	}

	protected void setForeground(RGB foregroundRGB) {
		if (monitor != null)
			return;
		if (this.foreground != null)
			this.foreground.dispose();
		this.foreground = new Color(getSplash().getShell().getDisplay(),
				foregroundRGB);
	}
	
	protected Color getForeground() {
		return foreground;
	}

	protected void setMessageRect(Rectangle messageRect) {
		this.messageRect = messageRect;
	}

	protected void setProgressRect(Rectangle progressRect) {
		this.progressRect = progressRect;
	}
	
	protected Composite getContent() {
		return (Composite) getBundleProgressMonitor();
	}
	
	private void updateUI(final Runnable r) {
		Shell splashShell = getSplash();
		if (splashShell == null || splashShell.isDisposed())
			return;
		
		Display display = splashShell.getDisplay();
		
		if (Thread.currentThread() == display.getThread())
			r.run(); // run immediatley if we're on the UI thread
		else {
			StartupRunnable startupRunnable = new StartupRunnable() {

				@Override
				public void runWithException() throws Throwable {
					r.run();
				}
			};
			display.asyncExec(startupRunnable);
		}
	}
}

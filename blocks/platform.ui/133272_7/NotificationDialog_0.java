
package org.eclipse.jface.dialogs;

import java.util.function.Function;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class NotificationDialog {

	private class NotificationDialogImplementation extends PopupDialog implements FocusListener {

		private int deltaToAlpha = 0;

		private int stepToAddToAlpha = 10;

		private long delayInMillisecondsForJobReschedule;

		private boolean mayAutomaticallyFadeOut = true;

		private boolean hasFocus = false;

		protected boolean sticky;

		protected long durationInSecondsForFadeIn = 5;

		protected long durationInSecondsForShow = 15;

		protected long durationInSecondsForFadeOut = 10;

		private Job jobForFadeInOrShowOrFadeOut = new Job(NotificationDialog.class.getName()) {

			@Override
			protected IStatus run(IProgressMonitor monitor) {

				Shell lShell = getShell();
				Display lDisplay = lShell.getDisplay();

				if (lDisplay.isDisposed()) {
				} else {

					lDisplay.asyncExec(new Runnable() {
						@Override
						public void run() {

							if (lDisplay.isDisposed()) {
								close();
							} else {

								mayAutomaticallyFadeOut = true;

								if (sticky) {
									mayAutomaticallyFadeOut = false;
								} else {

									if (hasFocus) {
										mayAutomaticallyFadeOut = false;

									} else
									if (lShell.getBounds().contains(lDisplay.getCursorLocation())) {
										mayAutomaticallyFadeOut = false;
									}

									if (!mayAutomaticallyFadeOut) {
										lShell.setAlpha(255);
									}
								}

								int lAlphaCurrent = lShell.getAlpha();
								int lAlphaNew;

								if (lAlphaCurrent == 0) {
									deltaToAlpha = stepToAddToAlpha;

									delayInMillisecondsForJobReschedule = (durationInSecondsForFadeIn * 1000)
											/ (255 / stepToAddToAlpha);

								} else if (lAlphaCurrent == 255) {

									if (deltaToAlpha != 0) {
										deltaToAlpha = 0;

										delayInMillisecondsForJobReschedule = (durationInSecondsForShow * 1000);

									} else
									if (deltaToAlpha == 0 && mayAutomaticallyFadeOut) {
										deltaToAlpha = -stepToAddToAlpha;

										delayInMillisecondsForJobReschedule = (durationInSecondsForFadeOut * 1000)
												/ (255 / stepToAddToAlpha);
									}
								}

								lAlphaNew = Integer.max(0, Integer.min(255, lAlphaCurrent + deltaToAlpha));
								lShell.setAlpha(lAlphaNew);

								if (lAlphaNew == 0) {
									delayInMillisecondsForJobReschedule = 0;
								}
							}

							if (delayInMillisecondsForJobReschedule > 0) {
								schedule(delayInMillisecondsForJobReschedule);
							}

						}

					});

				}
				if (monitor.isCanceled()) {
					return Status.CANCEL_STATUS;
				}

				return Status.OK_STATUS;

			}
		};

		public NotificationDialogImplementation() {
			super((Shell) null, PopupDialog.HOVER_SHELLSTYLE, false, false, false, false, false, textTitleMenuArea,
					textInfoTextArea);

		}

		private void addFocusListener(Composite pComposite) {

			pComposite.addFocusListener(this);

			for (Control lChild : pComposite.getChildren()) {

				lChild.addFocusListener(this);

				if (lChild instanceof Composite) {

					addFocusListener((Composite) lChild);
				}

			}

		}

		@Override
		public boolean close() {


			jobForFadeInOrShowOrFadeOut.cancel();
			getShell().setAlpha(0);

			return true;
		}

		private void configureLocation(Point size) {

			Shell shell = getShell();

			Shell workbenchWindowShell = (Shell) shell.getParent();
			int xCoord;
			int yCoord;
			if (workbenchWindowShell != null) {
				Rectangle workbenchWindowBounds = workbenchWindowShell.getBounds();
				xCoord = workbenchWindowBounds.x + workbenchWindowBounds.width - size.x - 10;
				yCoord = workbenchWindowBounds.y + workbenchWindowBounds.height - size.y - 10;

			} else {

				Rectangle lBounds = shell.getDisplay().getPrimaryMonitor().getBounds();

				xCoord = lBounds.x + lBounds.width - size.x;
				yCoord = lBounds.y + lBounds.height - size.y;

			}
			Rectangle bounds = new Rectangle(xCoord, yCoord, size.x, size.y);
			shell.setBounds(getConstrainedShellBounds(bounds));
		}

		private Point configureSize() {
			Shell shell = getShell();

			shell.pack();
			Point size = shell.getSize();

			Shell workbenchWindowShell = (Shell) shell.getParent();
			if (workbenchWindowShell != null) {
				Point workbenchWindowSize = workbenchWindowShell.getSize();
				int maxWidth = workbenchWindowSize.x * 2 / 5;
				int maxHeight = workbenchWindowSize.y / 2;
				if (size.x > maxWidth) {
					size.x = maxWidth;
				}
				if (size.y > maxHeight) {
					size.y = maxHeight;
				}
			}

			shell.setSize(size);
			return size;
		}

		@Override
		protected Control createDialogArea(Composite parent) {

			Control lResult = functionCreateDialogArea.apply(parent);

			addFocusListener(parent);

			return lResult;

		}

		@Override
		protected Control createInfoTextArea(Composite parent) {

			Control lResult = null;

			if (functionCreateInfoTextArea != null) {
				lResult = functionCreateInfoTextArea.apply(parent);
			} else {
				lResult = super.createInfoTextArea(parent);
			}

			addFocusListener(parent);

			return lResult;
		}

		@Override
		protected Control createTitleMenuArea(Composite parent) {
			Control lResult = null;

			if (functionCreateInfoTextArea != null) {
				lResult = functionCreateTitleMenuArea.apply(parent);
			} else {
				lResult = super.createTitleMenuArea(parent);
			}

			addFocusListener(parent);

			return lResult;
		}

		@Override
		public void focusGained(FocusEvent e) {
			hasFocus = true;
		}

		@Override
		public void focusLost(FocusEvent e) {
			hasFocus = false;
		}

		@Override
		public int open() {

			if (getShell() != null) {
				close();
			}

			create();

			Point size = configureSize();
			configureLocation(size);



			getShell().setAlpha(0);
			getShell().setVisible(true);

			jobForFadeInOrShowOrFadeOut.schedule();

			return Window.OK;

		}

	}

	public static NotificationDialog _new() {

		NotificationDialog lResult = new NotificationDialog();

		return lResult;

	}

	private NotificationDialogImplementation popupDialogImplementation;

	private Function<Composite, Control> functionCreateTitleMenuArea;

	private Function<Composite, Control> functionCreateDialogArea;

	private Function<Composite, Control> functionCreateInfoTextArea;

	private String textTitleMenuArea;

	private String textInfoTextArea;

	private NotificationDialog() {

	}

	public NotificationDialog close() {

		popupDialogImplementation.close();

		return this;

	}

	public NotificationDialog functionCreateInfoTextArea(Function<Composite, Control> pFunction) {

		functionCreateInfoTextArea = pFunction;

		return this;

	}

	public NotificationDialog functionCreateDialogArea(Function<Composite, Control> pFunction) {

		functionCreateDialogArea = pFunction;

		return this;

	}

	public NotificationDialog functionCreateTitleMenuArea(Function<Composite, Control> pFunction) {

		functionCreateTitleMenuArea = pFunction;

		return this;

	}

	public NotificationDialog open() {

		popupDialogImplementation = new NotificationDialogImplementation();

		popupDialogImplementation.open();

		return this;

	}

	public NotificationDialog textInfoTextArea(String pText) {

		if (pText != null) {
			if (functionCreateInfoTextArea != null) {
				throw new RuntimeException("Either simple text or functionCreateInfoTextArea"); //$NON-NLS-1$
			}
		}

		textInfoTextArea = pText;

		return this;

	}

	public NotificationDialog textTitleMenuArea(String pText) {

		if (pText != null) {
			if (functionCreateTitleMenuArea != null) {
				throw new RuntimeException("Either simple text or functionCreateTitleMenuArea"); //$NON-NLS-1$
			}
		}

		textTitleMenuArea = pText;

		return this;

	}

	public NotificationDialog sticky(boolean pSticky) {

		popupDialogImplementation.sticky = pSticky;

		return this;

	}

	public NotificationDialog sticky() {

		sticky(true);

		return this;

	}

	public NotificationDialog duration(long pDurationInSecondsForFadeIn, long pDurationInSecondsForShow,
			long pDurationInSecondsForFadeOut) {

		popupDialogImplementation.durationInSecondsForFadeIn = pDurationInSecondsForFadeIn;
		popupDialogImplementation.durationInSecondsForShow = pDurationInSecondsForShow;
		popupDialogImplementation.durationInSecondsForFadeOut = pDurationInSecondsForFadeOut;

		return this;

	}

}

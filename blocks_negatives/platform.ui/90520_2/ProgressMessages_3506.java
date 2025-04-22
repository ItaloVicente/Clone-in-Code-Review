/*******************************************************************************
 * Copyright (c) 2003, 2015 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Lars Vogel <Lars.Vogel@gmail.com> - Bug 422040
 *******************************************************************************/
package org.eclipse.e4.ui.progress.internal;

import java.util.Arrays;
import java.util.Comparator;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.model.application.ui.basic.MWindow;
import org.eclipse.e4.ui.progress.IProgressConstants;
import org.eclipse.e4.ui.progress.IProgressService;
import org.eclipse.e4.ui.progress.internal.legacy.StatusUtil;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.e4.ui.workbench.modeling.EPartService.PartState;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.osgi.service.log.LogService;

/**
 * The ProgressUtil is a class that contains static utility methods used for the
 * progress API.
 */

public class ProgressManagerUtil {

	@SuppressWarnings("unchecked")
	static class ProgressViewerComparator extends ViewerComparator {
		@Override
		@SuppressWarnings("rawtypes")
		public int compare(Viewer testViewer, Object e1, Object e2) {
			return ((Comparable) e1).compareTo(e2);
		}

		@Override
		public void sort(final Viewer viewer, Object[] elements) {
			/*
			 *
			 * This ordering is inherently unstable, since it relies on
			 * modifiable properties of the elements: E.g. the default
			 * implementation in JobTreeElement compares getDisplayString(),
			 * many of whose implementations use getPercentDone().
			 *
			 * JavaSE 7+'s TimSort introduced a breaking change: It now throws a
			 * new IllegalArgumentException for bad comparators. Workaround is
			 * to retry a few times.
			 */
			for (int retries = 3; retries > 0; retries--) {
				try {
					Arrays.sort(elements, new Comparator<Object>() {
						@Override
						public int compare(Object a, Object b) {
							return ProgressViewerComparator.this.compare(viewer, a, b);
						}
					});
				} catch (IllegalArgumentException e) {
				}
			}

			super.sort(viewer, elements);
		}
	}

	/**
	 * A constant used by the progress support to determine if an operation is
	 * too short to show progress.
	 */
	public static long SHORT_OPERATION_TIME = 250;

	static final QualifiedName KEEP_PROPERTY = IProgressConstants.KEEP_PROPERTY;

	static final QualifiedName KEEPONE_PROPERTY = IProgressConstants.KEEPONE_PROPERTY;

	static final Object[] EMPTY_OBJECT_ARRAY = new Object[0];

	static final QualifiedName INFRASTRUCTURE_PROPERTY = new QualifiedName(
			IProgressConstants.PLUGIN_ID, "INFRASTRUCTURE_PROPERTY");//$NON-NLS-1$

	private static String ellipsis = ProgressMessages.ProgressFloatingWindow_EllipsisValue;

	/**
	 * Return a status for the exception.
	 *
	 * @param exception
	 * @return IStatus
	 */
	static IStatus exceptionStatus(Throwable exception) {
		return StatusUtil.newStatus(IStatus.ERROR,
				exception.getMessage() == null ? "" : exception.getMessage(), //$NON-NLS-1$
				exception);
	}

	/**
	 * Log the exception for debugging.
	 *
	 * @param exception
	 */
	static void logException(Throwable exception) {
		Services.getInstance().getLogService().log(LogService.LOG_ERROR,
		        exception.getMessage() == null ? "" : exception.getMessage(), //$NON-NLS-1$
		        exception);
	}

	/**
	 * Return a viewer comparator for looking at the jobs.
	 *
	 * @return ViewerComparator
	 */
	public static ViewerComparator getProgressViewerComparator() {
		return new ProgressViewerComparator();
	}

	/**
	 * Open the progress view in the supplied window.
	 *
	 * @param window
	 */
	public static void openProgressView() {
		Services services = Services.getInstance();
		MPart progressView = (MPart) services.getModelService().find(
		        ProgressManager.PROGRESS_VIEW_NAME, services.getMWindow());
		EPartService partService = services.getPartService();
		if (progressView == null) {
			progressView = partService.createPart(ProgressManager.PROGRESS_VIEW_NAME);
			if (progressView != null)
				partService.showPart(progressView, PartState.VISIBLE);
		}
		if (progressView == null) {
			return;
		}
		partService.activate(progressView);
	}

	/**
	 * Shorten the given text <code>t</code> so that its length doesn't exceed
	 * the given width. The default implementation replaces characters in the
	 * center of the original string with an ellipsis ("..."). Override if you
	 * need a different strategy.
	 *
	 * @param textValue
	 * @param control
	 * @return String
	 */

	static String shortenText(String textValue, Control control) {
		if (textValue == null) {
			return null;
		}
		GC gc = new GC(control);
		int maxWidth = control.getBounds().width - 5;
		int maxExtent = gc.textExtent(textValue).x;
		if (maxExtent < maxWidth) {
			gc.dispose();
			return textValue;
		}
		int length = textValue.length();
		int charsToClip = Math.round(0.95f * length
				* (1 - ((float) maxWidth / maxExtent)));
		int secondWord = findSecondWhitespace(textValue, gc, maxWidth);
		int pivot = ((length - secondWord) / 2) + secondWord;
		int start = pivot - (charsToClip / 2);
		int end = pivot + (charsToClip / 2) + 1;
		while (start >= 0 && end < length) {
			String s1 = textValue.substring(0, start);
			String s2 = textValue.substring(end, length);
			String s = s1 + ellipsis + s2;
			int l = gc.textExtent(s).x;
			if (l < maxWidth) {
				gc.dispose();
				return s;
			}
			start--;
			end++;
		}
		gc.dispose();
		return textValue;
	}

	/**
	 * Find the second index of a whitespace. Return the first index if there
	 * isn't one or 0 if there is no space at all.
	 *
	 * @param textValue
	 * @param gc
	 *            The GC to test max length
	 * @param maxWidth
	 *            The maximim extent
	 * @return int
	 */
	private static int findSecondWhitespace(String textValue, GC gc,
			int maxWidth) {
		int firstCharacter = 0;
		char[] chars = textValue.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			if (Character.isWhitespace(chars[i])) {
				firstCharacter = i;
				break;
			}
		}
		if (firstCharacter == 0) {
			return 0;
		}
		int secondCharacter = firstCharacter;
		for (int i = firstCharacter; i < chars.length; i++) {
			if (Character.isWhitespace(chars[i])) {
				secondCharacter = i;
				break;
			}
		}
		if (gc.textExtent(textValue.substring(0, secondCharacter)).x > maxWidth) {
			if (gc.textExtent(textValue.substring(0, firstCharacter)).x > maxWidth) {
				return 0;
			}
			return firstCharacter;
		}
		return secondCharacter;
	}

	/**
	 * If there are any modal shells open reschedule openJob to wait until they
	 * are closed. Return true if it rescheduled, false if there is nothing
	 * blocking it.
	 *
	 * @param openJob
	 * @return boolean. true if the job was rescheduled due to modal dialogs.
	 */
	public static boolean rescheduleIfModalShellOpen(Job openJob,
	        IProgressService progressService) {
		Shell modal = getModalShellExcluding(null);
		if (modal == null) {
			return false;
		}

		openJob.schedule(progressService.getLongOperationTime());
		return true;
	}

	/**
	 * Return whether or not it is safe to open this dialog. If so then return
	 * <code>true</code>. If not then set it to open itself when it has had
	 * ProgressManager#longOperationTime worth of ticks.
	 *
	 * @param dialog
	 *            ProgressMonitorJobsDialog that will be opening
	 * @param excludedShell
	 *            The shell
	 * @return boolean. <code>true</code> if it can open. Otherwise return
	 *         false and set the dialog to tick.
	 */
	public static boolean safeToOpen(ProgressMonitorJobsDialog dialog,
			Shell excludedShell) {
		Shell modal = getModalShellExcluding(excludedShell);
		if (modal == null) {
			return true;
		}

		dialog.watchTicks();
		return false;
	}

	/**
	 * Return the modal shell that is currently open. If there isn't one then
	 * return null. If there are stacked modal shells, return the top one.
	 *
	 * @param shell
	 *            A shell to exclude from the search. May be <code>null</code>.
	 *
	 * @return Shell or <code>null</code>.
	 */

	public static Shell getModalShellExcluding(Shell shell) {

		if (shell == null || shell.isDisposed()) {
			return getModalChildExcluding(Services.getInstance().getShell()
			        .getShells(), shell);
		}

		return getModalChildExcluding(shell.getShells(), shell);
	}

	/**
	 * Return the modal shell that is currently open. If there isn't one then
	 * return null.
	 *
	 * @param toSearch shells to search for modal children
	 * @param toExclude shell to ignore
	 * @return the most specific modal child, or null if none
	 */
	private static Shell getModalChildExcluding(Shell[] toSearch, Shell toExclude) {
		int modal = SWT.APPLICATION_MODAL | SWT.SYSTEM_MODAL
				| SWT.PRIMARY_MODAL;


		for (int i = toSearch.length - 1; i >= 0; i--) {
			Shell shell = toSearch[i];
			if(shell.equals(toExclude)) {
				continue;
			}

			Shell[] children = shell.getShells();
			Shell modalChild = getModalChildExcluding(children, toExclude);
			if (modalChild != null) {
				return modalChild;
			}

			if (shell.isVisible() && (shell.getStyle() & modal) != 0) {
				return shell;
			}
		}

		return null;
	}

	/**
	 * Utility method to get the best parenting possible for a dialog. If there
	 * is a modal shell create it so as to avoid two modal dialogs. If not then
	 * return the shell of the active workbench window. If neither can be found
	 * return null.
	 *
	 * @return Shell or <code>null</code>
	 */
	public static Shell getDefaultParent() {
		Shell modal = getModalShellExcluding(null);
		if (modal != null) {
			return modal;
		}

		return getNonModalShell();
	}

	/**
	 * Get the active non modal shell. If there isn't one return null.
	 *
	 * @return Shell
	 */
	public static Shell getNonModalShell() {
		MApplication application = Services.getInstance().getMApplication();
		if (application == null) {
			return null;
		}
		MWindow window = application.getSelectedElement();
		if (window != null) {
			Object widget = window.getWidget();
			if (widget instanceof Shell) {
				return (Shell) widget;
			}
		}
		for (MWindow child : application.getChildren()) {
			Object widget = child.getWidget();
			if (widget instanceof Shell) {
				return (Shell) widget;
			}
		}
		return null;
	}







}

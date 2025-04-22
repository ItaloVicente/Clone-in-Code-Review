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
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.osgi.service.log.LogService;


public class ProgressManagerUtil {

	@SuppressWarnings("unchecked")
	static class ProgressViewerComparator extends ViewerComparator {
		@SuppressWarnings("rawtypes")
		public int compare(Viewer testViewer, Object e1, Object e2) {
			return ((Comparable) e1).compareTo(e2);
		}

		@Override
		public void sort(final Viewer viewer, Object[] elements) {
			for (int retries = 3; retries > 0; retries--) {
				try {
					Arrays.sort(elements, new Comparator<Object>() {
						public int compare(Object a, Object b) {
							return ProgressViewerComparator.this.compare(viewer, a, b);
						}
					});
					return; // success
				} catch (IllegalArgumentException e) {
				}
			}

			super.sort(viewer, elements);
		}
	}

	public static long SHORT_OPERATION_TIME = 250;

	static final QualifiedName KEEP_PROPERTY = IProgressConstants.KEEP_PROPERTY;

	static final QualifiedName KEEPONE_PROPERTY = IProgressConstants.KEEPONE_PROPERTY;

	static final Object[] EMPTY_OBJECT_ARRAY = new Object[0];

	static final QualifiedName INFRASTRUCTURE_PROPERTY = new QualifiedName(
			IProgressConstants.PLUGIN_ID, "INFRASTRUCTURE_PROPERTY");//$NON-NLS-1$

	private static String ellipsis = ProgressMessages.ProgressFloatingWindow_EllipsisValue;

	static IStatus exceptionStatus(Throwable exception) {
		return StatusUtil.newStatus(IStatus.ERROR,
				exception.getMessage() == null ? "" : exception.getMessage(), //$NON-NLS-1$
				exception);
	}

	static void logException(Throwable exception) {
		Services.getInstance().getLogService().log(LogService.LOG_ERROR,
		        exception.getMessage() == null ? "" : exception.getMessage(),
		        exception);
	}

	public static ViewerComparator getProgressViewerComparator() {
		return new ProgressViewerComparator();
	}

	public static void openProgressView() {
		Services services = Services.getInstance();
		MPart progressView = (MPart) services.getModelService().find(
		        ProgressManager.PROGRESS_VIEW_NAME, services.getMWindow());
		if (progressView == null) {
			return;
		}
		services.getPartService().activate(progressView);
	}


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

	public static boolean rescheduleIfModalShellOpen(Job openJob,
	        IProgressService progressService) {
		Shell modal = getModalShellExcluding(null);
		if (modal == null) {
			return false;
		}

		openJob.schedule(progressService.getLongOperationTime());
		return true;
	}

	public static boolean safeToOpen(ProgressMonitorJobsDialog dialog,
			Shell excludedShell) {
		Shell modal = getModalShellExcluding(excludedShell);
		if (modal == null) {
			return true;
		}

		dialog.watchTicks();
		return false;
	}
	

	public static Shell getModalShellExcluding(Shell shell) {

		if (shell == null || shell.isDisposed()) {
			return getModalChildExcluding(Services.getInstance().getShell()
			        .getShells(), shell);
		}

		return getModalChildExcluding(shell.getShells(), shell);
	}
	        
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
	 
	public static Shell getDefaultParent() {
		Shell modal = getModalShellExcluding(null);
		if (modal != null) {
			return modal;
		}

		return getNonModalShell();
	}

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
	

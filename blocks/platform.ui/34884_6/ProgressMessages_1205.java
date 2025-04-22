package org.eclipse.ui.internal.progress;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Comparator;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.basic.MWindow;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.jface.window.IShellProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.AnimationEngine;
import org.eclipse.ui.internal.WorkbenchPlugin;
import org.eclipse.ui.internal.WorkbenchWindow;
import org.eclipse.ui.internal.misc.StatusUtil;
import org.eclipse.ui.internal.util.BundleUtility;
import org.eclipse.ui.progress.IProgressConstants;
import org.eclipse.ui.views.IViewDescriptor;


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
			for (int retries = 3; retries > 0; retries--) {
				try {
					Arrays.sort(elements, new Comparator<Object>() {
						@Override
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
			WorkbenchPlugin.PI_WORKBENCH, "INFRASTRUCTURE_PROPERTY");//$NON-NLS-1$

	private static String ellipsis = ProgressMessages.ProgressFloatingWindow_EllipsisValue;

	static IStatus exceptionStatus(Throwable exception) {
		return StatusUtil.newStatus(IStatus.ERROR,
				exception.getMessage() == null ? "" : exception.getMessage(), //$NON-NLS-1$
				exception);
	}

	static void logException(Throwable exception) {
		BundleUtility.log(PlatformUI.PLUGIN_ID, exception);
	}

	static ViewerComparator getProgressViewerComparator() {
		return new ProgressViewerComparator();
	}

	static void openProgressView(WorkbenchWindow window) {
		IWorkbenchPage page = window.getActivePage();
		if (page == null) {
			return;
		}
		try {
			IViewDescriptor reference = WorkbenchPlugin.getDefault()
					.getViewRegistry()
					.find(IProgressConstants.PROGRESS_VIEW_ID);

			if (reference == null) {
				return;
			}
			page.showView(IProgressConstants.PROGRESS_VIEW_ID);
		} catch (PartInitException exception) {
			logException(exception);
		}
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

	public static boolean rescheduleIfModalShellOpen(Job openJob) {
		Shell modal = getModalShellExcluding(null);
		if (modal == null) {
			return false;
		}

		openJob.schedule(PlatformUI.getWorkbench().getProgressService()
				.getLongOperationTime());
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
			return getModalChildExcluding(PlatformUI.getWorkbench()
					.getDisplay().getShells(), shell);
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
		MApplication application = (MApplication) PlatformUI.getWorkbench().getService(
				MApplication.class);
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

	public static void animateDown(Rectangle startPosition) {
		IWorkbenchWindow currentWindow = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow();
		if (currentWindow == null) {
			return;
		}
		WorkbenchWindow internalWindow = (WorkbenchWindow) currentWindow;

		ProgressRegion progressRegion = internalWindow.getProgressRegion();
		if (progressRegion == null) {
			return;
		}
		Rectangle endPosition = progressRegion.getControl().getBounds();

		Point windowLocation = internalWindow.getShell().getLocation();
		endPosition.x += windowLocation.x;
		endPosition.y += windowLocation.y;

		AnimationEngine.createTweakedAnimation(internalWindow.getShell(), 400, startPosition, endPosition);
	}

	public static void animateUp(Rectangle endPosition) {
		IWorkbenchWindow currentWindow = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow();
		if (currentWindow == null) {
			return;
		}
		WorkbenchWindow internalWindow = (WorkbenchWindow) currentWindow;
		Point windowLocation = internalWindow.getShell().getLocation();

		ProgressRegion progressRegion = internalWindow.getProgressRegion();
		if (progressRegion == null) {
			return;
		}
		Rectangle startPosition = progressRegion.getControl().getBounds();
		startPosition.x += windowLocation.x;
		startPosition.y += windowLocation.y;

		AnimationEngine.createTweakedAnimation(internalWindow.getShell(), 400, startPosition, endPosition);
	}

	static IShellProvider getShellProvider() {
		return new IShellProvider() {

			@Override
			public Shell getShell() {
				return getDefaultParent();
			}
		};
	}

	public static URL getIconsRoot() {
		return BundleUtility.find(PlatformUI.PLUGIN_ID,
				ProgressManager.PROGRESS_FOLDER);
	}

	public static URL getProgressSpinnerLocation() {
		try {
			return new URL(getIconsRoot(), "progress_spinner.png");//$NON-NLS-1$
		} catch (MalformedURLException e) {
			return null;
		}
	}
}

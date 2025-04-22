
package org.eclipse.ui.views.markers.internal;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.ide.StatusUtil;
import org.eclipse.ui.statushandlers.StatusManager;
import org.eclipse.ui.views.markers.internal.MarkerAdapter.MarkerCategory;

public class ActionSelectAll extends MarkerSelectionProviderAction {

	private MarkerView view;

	public ActionSelectAll(MarkerView markerView) {
		super(markerView.getViewer(), MarkerMessages.selectAllAction_title);
		setEnabled(true);
		view = markerView;
	}

	@Override
	public void run() {

		if (view.getMarkerAdapter().isBuilding())
			return;

		IRunnableWithProgress selectionRunnableWithProgress = new IRunnableWithProgress() {
			@Override
			public void run(IProgressMonitor monitor) {

				try {
					monitor.beginTask(MarkerMessages.selectAllAction_title,
							 100);
					monitor.subTask(MarkerMessages.selectAllAction_calculating);
					if (view.getMarkerAdapter().isShowingHierarchy()) {

						if (monitor.isCanceled())
							return;

						monitor.worked(10);
						PlatformUI.getWorkbench().getDisplay()
								.readAndDispatch();
						MarkerCategory[] categories = view.getMarkerAdapter()
								.getCategories();
						int totalSize = 0;
						for (int i = 0; i < categories.length; i++) {
							MarkerCategory markerCategory = categories[i];
							totalSize += markerCategory.getDisplayedSize();
						}
						monitor.worked(10);
						PlatformUI.getWorkbench().getDisplay()
								.readAndDispatch();
						Object[] selection = new Object[totalSize];
						int index = 0;
						for (int i = 0; i < categories.length; i++) {
							MarkerNode[] children = categories[i].getChildren();
							System.arraycopy(children, 0, selection, index,
									children.length);
							index += children.length;
						}
						monitor.worked(10);
						if (monitor.isCanceled())
							return;
						PlatformUI.getWorkbench().getDisplay()
								.readAndDispatch();
						monitor
								.subTask(MarkerMessages.selectAllAction_applying);
						getSelectionProvider().setSelection(
								new StructuredSelection(selection));
					} else {
						if (monitor.isCanceled())
							return;
						monitor.worked(10);
						List selection = view.getMarkerAdapter()
								.getCurrentMarkers().asList();
						monitor.worked(10);
						monitor
								.subTask(MarkerMessages.selectAllAction_applying);
						PlatformUI.getWorkbench().getDisplay()
								.readAndDispatch();
						getSelectionProvider().setSelection(
								new StructuredSelection(selection));
					}
				} finally {
					monitor.done();
				}

			}
		};

		try {
			PlatformUI.getWorkbench().getProgressService().run(false, false,
					selectionRunnableWithProgress);
		} catch (InvocationTargetException e) {
			StatusManager.getManager().handle(
					StatusUtil.newStatus(IStatus.ERROR,
							e.getLocalizedMessage(), e), StatusManager.LOG);
		} catch (InterruptedException e) {
			StatusManager.getManager().handle(
					StatusUtil.newStatus(IStatus.ERROR,
							e.getLocalizedMessage(), e), StatusManager.LOG);
		}

	}

	@Override
	public void selectionChanged(IStructuredSelection selection) {
		setEnabled(!selection.isEmpty());
	}
}

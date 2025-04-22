
package org.eclipse.ui.views.markers.internal;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableContext;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.osgi.util.NLS;
import org.eclipse.ui.IMarkerResolution;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.internal.ide.IDEInternalWorkbenchImages;
import org.eclipse.ui.internal.ide.IDEWorkbenchPlugin;
import org.eclipse.ui.progress.IWorkbenchSiteProgressService;

public class ActionResolveMarker extends MarkerSelectionProviderAction {

	private MarkerView view;

	public ActionResolveMarker(MarkerView markerView,
			ISelectionProvider provider) {
		super(provider, MarkerMessages.resolveMarkerAction_title);
		setEnabled(false);
		setImageDescriptor(IDEInternalWorkbenchImages
				.getImageDescriptor(IDEInternalWorkbenchImages.IMG_ELCL_QUICK_FIX_ENABLED));
		setDisabledImageDescriptor(IDEInternalWorkbenchImages
				.getImageDescriptor(IDEInternalWorkbenchImages.IMG_DLCL_QUICK_FIX_DISABLED));
		view = markerView;
	}

	@Override
	public void run() {

		IRunnableContext context = new ProgressMonitorDialog(view.getSite()
				.getShell());
		final Object[] resolutions = new Object[1];

		IRunnableWithProgress resolutionsRunnable = new IRunnableWithProgress() {
			@Override
			public void run(IProgressMonitor monitor) {
				monitor.beginTask(NLS.bind(
						MarkerMessages.resolveMarkerAction_computationAction,
						getMarkerDescription()), 100);
				monitor.worked(25);
				resolutions[0] = IDE.getMarkerHelpRegistry().getResolutions(
						getSelectedMarker());
				monitor.done();
			}
		};

		Object service = view.getSite().getAdapter(
				IWorkbenchSiteProgressService.class);

		try {
			if (service == null) {
				PlatformUI.getWorkbench().getProgressService().runInUI(context,
						resolutionsRunnable, null);
			} else {
				((IWorkbenchSiteProgressService) service).runInUI(context,
						resolutionsRunnable, null);
			}
		} catch (InvocationTargetException exception) {
			handleException(exception);
			return;
		} catch (InterruptedException exception) {
			handleException(exception);
			return;
		}

		IMarkerResolution[] foundResolutions = (IMarkerResolution[]) resolutions[0];
		if (foundResolutions.length == 0)
			MessageDialog
					.openInformation(
							view.getSite().getShell(),
							MarkerMessages.MarkerResolutionDialog_CannotFixTitle,
							NLS
									.bind(
											MarkerMessages.MarkerResolutionDialog_CannotFixMessage,
											getMarkerDescription()));
		else {
			Dialog dialog = new MarkerResolutionDialog(view.getSite()
					.getShell(), getSelectedMarker(), foundResolutions, view);
			dialog.open();
		}

	}

	private void handleException(Exception exception) {
		IDEWorkbenchPlugin.log(exception.getLocalizedMessage(), exception);
		ErrorDialog.openError(view.getSite().getShell(), MarkerMessages.Error,
				NLS.bind(
						MarkerMessages.MarkerResolutionDialog_CannotFixMessage,
						getMarkerDescription()), Util.errorStatus(exception));
	}

	private String getMarkerDescription() {
		return Util.getProperty(IMarker.MESSAGE, getSelectedMarker());
	}

	@Override
	public void selectionChanged(IStructuredSelection selection) {

		if (Util.isSingleConcreteSelection(selection)) {
			if (IDE.getMarkerHelpRegistry().hasResolutions(getSelectedMarker())) {
				setEnabled(true);
				return;
			}
		}

		setEnabled(false);
	}
}


package org.eclipse.ui.views.markers.internal;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWTError;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.MarkerTransfer;

public class ActionCopyMarker extends MarkerSelectionProviderAction {

	private IWorkbenchPart part;

	private Clipboard clipboard;

	private IField[] properties;

	public ActionCopyMarker(IWorkbenchPart part, ISelectionProvider provider) {
		super(provider, MarkerMessages.copyAction_title);
		this.part = part;
		setImageDescriptor(PlatformUI.getWorkbench().getSharedImages()
				.getImageDescriptor(ISharedImages.IMG_TOOL_COPY));
		setEnabled(false);
	}

	void setClipboard(Clipboard clipboard) {
		this.clipboard = clipboard;
	}

	void setProperties(IField[] properties) {
		this.properties = properties;
	}

	@Override
	public void run() {
		IMarker[] markers = getSelectedMarkers();
		setClipboard(markers, createMarkerReport(markers));
	}

	@Override
	public void selectionChanged(IStructuredSelection selection) {
		setEnabled(Util.allConcreteSelection(selection));
	}

	private void setClipboard(IMarker[] markers, String markerReport) {
		try {
			Object[] data;
			Transfer[] transferTypes;
			if (markerReport == null) {
				data = new Object[] { markers };
				transferTypes = new Transfer[] { MarkerTransfer.getInstance() };
			} else {
				data = new Object[] { markers, markerReport };
				transferTypes = new Transfer[] { MarkerTransfer.getInstance(),
						TextTransfer.getInstance() };
			}

			clipboard.setContents(data, transferTypes);
		} catch (SWTError e) {
			if (e.code != DND.ERROR_CANNOT_SET_CLIPBOARD) {
				throw e;
			}
			if (MessageDialog.openQuestion(part.getSite().getShell(),
					MarkerMessages.CopyToClipboardProblemDialog_title,
					MarkerMessages.CopyToClipboardProblemDialog_message)) {
				setClipboard(markers, markerReport);
			}
		}
	}

	String createMarkerReport(IMarker[] rawMarkers) {
		ConcreteMarker[] markers;
		try {
			markers = MarkerList.createMarkers(rawMarkers);
		} catch (CoreException e) {
			ErrorDialog.openError(part.getSite().getShell(),
					MarkerMessages.Error, null, e.getStatus());
			return ""; //$NON-NLS-1$
		}

		StringBuffer report = new StringBuffer();

		final String NEWLINE = System.getProperty("line.separator"); //$NON-NLS-1$
		final char DELIMITER = '\t';

		if (properties == null) {
			return null;
		}

		for (int i = 0; i < properties.length; i++) {
			report.append(properties[i].getDescription());
			if (i == properties.length - 1) {
				report.append(NEWLINE);
			} else {
				report.append(DELIMITER);
			}
		}

		for (int i = 0; i < markers.length; i++) {
			ConcreteMarker marker = markers[i];
			for (int j = 0; j < properties.length; j++) {
				report.append(properties[j].getValue(marker));
				if (j == properties.length - 1) {
					report.append(NEWLINE);
				} else {
					report.append(DELIMITER);
				}
			}
		}

		return report.toString();
	}
}

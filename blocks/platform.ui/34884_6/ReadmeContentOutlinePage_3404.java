package org.eclipse.ui.examples.readmetool;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.dnd.DragSourceAdapter;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.ui.part.PluginTransfer;
import org.eclipse.ui.part.PluginTransferData;

public class ReadmeContentOutlineDragListener extends DragSourceAdapter {
    private ReadmeContentOutlinePage page;

    public ReadmeContentOutlineDragListener(ReadmeContentOutlinePage page) {
        this.page = page;
    }

    public void dragSetData(DragSourceEvent event) {
        if (PluginTransfer.getInstance().isSupportedType(event.dataType)) {
            byte[] segmentData = getSegmentText().getBytes();
            event.data = new PluginTransferData(ReadmeDropActionDelegate.ID,
                    segmentData);
            return;
        }
        if (TextTransfer.getInstance().isSupportedType(event.dataType)) {
            event.data = getSegmentText();
            return;
        }
    }

    private String getSegmentText() {
        StringBuffer result = new StringBuffer();
        ISelection selection = page.getSelection();
        if (selection instanceof org.eclipse.jface.viewers.IStructuredSelection) {
            Object[] selected = ((IStructuredSelection) selection).toArray();
            result.append("\n"); //$NON-NLS-1$
            for (int i = 0; i < selected.length; i++) {
                if (selected[i] instanceof MarkElement) {
                    result.append(((MarkElement) selected[i])
                            .getLabel(selected[i]));
                    result.append("\n"); //$NON-NLS-1$
                }
            }
        }
        return result.toString();
    }
}

package org.eclipse.ui.dialogs;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.osgi.util.TextProcessor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IFileEditorMapping;

public class FileEditorMappingLabelProvider extends LabelProvider implements
        ITableLabelProvider {

    public final static FileEditorMappingLabelProvider INSTANCE = new FileEditorMappingLabelProvider();

    private List imagesToDispose = new ArrayList();

    private FileEditorMappingLabelProvider() {
        super();
    }

    @Override
	public void dispose() {
        super.dispose();
        for (Iterator e = imagesToDispose.iterator(); e.hasNext();) {
            ((Image) e.next()).dispose();
        }
        imagesToDispose.clear();
    }

    @Override
	public Image getColumnImage(Object element, int row) {
        return getImage(element);
    }

    @Override
	public String getColumnText(Object element, int row) {
        return getText(element);
    }

    @Override
	public Image getImage(Object element) {
        if (element instanceof IFileEditorMapping) {
            Image image = ((IFileEditorMapping) element).getImageDescriptor()
                    .createImage();
            imagesToDispose.add(image);
            return image;
        }
        return null;
    }

    @Override
	public String getText(Object element) {
        if (element instanceof IFileEditorMapping) {
			return TextProcessor.process(((IFileEditorMapping) element)
					.getLabel(), "*."); //$NON-NLS-1$
		}

		return null;
    }
}

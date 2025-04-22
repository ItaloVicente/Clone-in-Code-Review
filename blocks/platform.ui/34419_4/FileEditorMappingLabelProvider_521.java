package org.eclipse.ui.dialogs;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.ui.IFileEditorMapping;

public class FileEditorMappingContentProvider implements
        IStructuredContentProvider {

    public final static FileEditorMappingContentProvider INSTANCE = new FileEditorMappingContentProvider();

    private FileEditorMappingContentProvider() {
        super();
    }

    @Override
	public void dispose() {
    }

    @Override
	public Object[] getElements(Object element) {
        IFileEditorMapping[] array = (IFileEditorMapping[]) element;
        return array == null ? new Object[0] : array;
    }

    @Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
    }
}

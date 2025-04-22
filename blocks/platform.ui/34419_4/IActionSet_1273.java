package org.eclipse.ui.internal.registry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.osgi.util.TextProcessor;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.IFileEditorMapping;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.activities.WorkbenchActivityHelper;
import org.eclipse.ui.internal.WorkbenchImages;

public class FileEditorMapping extends Object implements IFileEditorMapping, 
    Cloneable {
	
	private static final String STAR = "*"; //$NON-NLS-1$ 
	private static final String DOT = ".";	//$NON-NLS-1$ 
	
    private String name = STAR;

    private String extension;

    private List editors = new ArrayList(1);

    private List deletedEditors = new ArrayList(1);

	private List declaredDefaultEditors = new ArrayList(1);

    public FileEditorMapping(String extension) {
        this(STAR, extension); 
    }

    public FileEditorMapping(String name, String extension) {
        super();
        if (name == null || name.length() < 1) {
            setName(STAR);
        } else {
			setName(name);
		}
        if (extension == null) {
			setExtension("");//$NON-NLS-1$
		} else {
			setExtension(extension);
		}
    }

    public void addEditor(EditorDescriptor editor) {
        editors.add(editor);
        deletedEditors.remove(editor);
    }

    @Override
	public Object clone() {
        try {
            FileEditorMapping clone = (FileEditorMapping) super.clone();
            clone.editors = (List) ((ArrayList) editors).clone();
			clone.deletedEditors = (List) ((ArrayList) deletedEditors).clone();
			clone.declaredDefaultEditors = (List) ((ArrayList) declaredDefaultEditors).clone();
            return clone;
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }
	
    @Override
	public boolean equals(Object obj) {
        if (this == obj) {
			return true;
		}
        if (!(obj instanceof FileEditorMapping)) {
			return false;
		}
        FileEditorMapping mapping = (FileEditorMapping) obj;
        if (!this.name.equals(mapping.name)) {
			return false;
		}
        if (!this.extension.equals(mapping.extension)) {
			return false;
		}

        if (!compareList(this.editors, mapping.editors)) {
			return false;
		}
		if (!compareList(this.declaredDefaultEditors, mapping.declaredDefaultEditors)) {
			return false;
		}
        return compareList(this.deletedEditors, mapping.deletedEditors);
    }

    private boolean compareList(List l1, List l2) {
        if (l1.size() != l2.size()) {
			return false;
		}

        Iterator i1 = l1.iterator();
        Iterator i2 = l2.iterator();
        while (i1.hasNext() && i2.hasNext()) {
            Object o1 = i1.next();
            Object o2 = i2.next();
            if (!(o1 == null ? o2 == null : o1.equals(o2))) {
				return false;
			}
        }
        return true;
    }
	
    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((declaredDefaultEditors == null) ? 0 : declaredDefaultEditors.hashCode());
		result = prime * result + ((deletedEditors == null) ? 0 : deletedEditors.hashCode());
		result = prime * result + ((editors == null) ? 0 : editors.hashCode());
		result = prime * result + ((extension == null) ? 0 : extension.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

    @Override
	public IEditorDescriptor getDefaultEditor() {

        if (editors.size() == 0 || WorkbenchActivityHelper.restrictUseOf(editors.get(0))) {
			return null;
		}
        
        return (IEditorDescriptor) editors.get(0);
    }

    IEditorDescriptor[] getUnfilteredEditors() {
    	return (IEditorDescriptor[]) editors.toArray(new IEditorDescriptor[editors.size()]);
    }

    @Override
	public IEditorDescriptor[] getEditors() {
    	Collection descs = WorkbenchActivityHelper.restrictCollection(editors, new ArrayList());
		return (IEditorDescriptor[]) descs.toArray(new IEditorDescriptor[descs.size()]);
    }

    @Override
	public IEditorDescriptor[] getDeletedEditors() {
        IEditorDescriptor[] array = new IEditorDescriptor[deletedEditors.size()];
        deletedEditors.toArray(array);
        return array;
    }

    @Override
	public String getExtension() {
        return extension;
    }

    @Override
	public ImageDescriptor getImageDescriptor() {
        IEditorDescriptor editor = getDefaultEditor();
        if (editor == null) {
            return WorkbenchImages
                    .getImageDescriptor(ISharedImages.IMG_OBJ_FILE);
        } 
        return editor.getImageDescriptor();   
    }

    @Override
	public String getLabel() {
        return TextProcessor.process(name + (extension.length() == 0 ? "" : DOT + extension), STAR + DOT); 	//$NON-NLS-1$  
    }

    @Override
	public String getName() {
        return name;
    }

    public void removeEditor(EditorDescriptor editor) {
        editors.remove(editor);
        deletedEditors.add(editor);
        declaredDefaultEditors.remove(editor);
    }

    public void setDefaultEditor(EditorDescriptor editor) {
        editors.remove(editor);
        editors.add(0, editor);
        declaredDefaultEditors.remove(editor);
        declaredDefaultEditors.add(0, editor);
    }

    public void setEditorsList(List newEditors) {
        editors = newEditors;       
        declaredDefaultEditors.retainAll(newEditors);
    }

    public void setDeletedEditorsList(List newDeletedEditors) {
        deletedEditors = newDeletedEditors;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public void setName(String name) {
        this.name = name;
    }

	public IEditorDescriptor [] getDeclaredDefaultEditors() {
		Collection descs = WorkbenchActivityHelper.restrictCollection(declaredDefaultEditors, new ArrayList());
		return (IEditorDescriptor []) descs.toArray(new IEditorDescriptor[descs.size()]);
	}
	
	public boolean isDeclaredDefaultEditor (IEditorDescriptor editor) {
		return declaredDefaultEditors.contains(editor)
				&& !WorkbenchActivityHelper.restrictUseOf(editor);
	}

	public void setDefaultEditors(List defaultEditors) {
		declaredDefaultEditors = defaultEditors;		
	}
}

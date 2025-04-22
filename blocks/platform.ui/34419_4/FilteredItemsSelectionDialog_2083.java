package org.eclipse.ui.dialogs;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.internal.WorkbenchImages;
import org.eclipse.ui.internal.WorkbenchPlugin;
import org.eclipse.ui.model.AdaptableList;
import org.eclipse.ui.model.IWorkbenchAdapter;

public class FileSystemElement implements IAdaptable {
    private String name;

    private Object fileSystemObject;

    private AdaptableList folders = null;

    private AdaptableList files = null;

    private boolean isDirectory = false;

    private FileSystemElement parent;

    private IWorkbenchAdapter workbenchAdapter = new IWorkbenchAdapter() {
        @Override
		public Object[] getChildren(Object o) {
            return getFolders().getChildren(o);
        }

        @Override
		public Object getParent(Object o) {
            return parent;
        }

        @Override
		public String getLabel(Object o) {
            return name;
        }

        @Override
		public ImageDescriptor getImageDescriptor(Object object) {
            if (isDirectory()) {
                return WorkbenchImages
                        .getImageDescriptor(ISharedImages.IMG_OBJ_FOLDER);
            } else {
                return WorkbenchPlugin.getDefault().getEditorRegistry()
                        .getImageDescriptor(name);
            }
        }
    };

    public FileSystemElement(String name, FileSystemElement parent,
            boolean isDirectory) {
        this.name = name;
        this.parent = parent;
        this.isDirectory = isDirectory;
        if (parent != null) {
			parent.addChild(this);
		}
    }

    public void addChild(FileSystemElement child) {
        if (child.isDirectory()) {
            if (folders == null) {
				folders = new AdaptableList(1);
			}
            folders.add(child);
        } else {
            if (files == null) {
				files = new AdaptableList(1);
			}
            files.add(child);
        }
    }

    @Override
	public Object getAdapter(Class adapter) {
        if (adapter == IWorkbenchAdapter.class) {
            return workbenchAdapter;
        }
        return Platform.getAdapterManager().getAdapter(this, adapter);
    }

    public String getFileNameExtension() {
        int lastDot = name.lastIndexOf('.');
        return lastDot < 0 ? "" : name.substring(lastDot + 1); //$NON-NLS-1$
    }

    public AdaptableList getFiles() {
        if (files == null) {
            files = new AdaptableList(0);
        }
        return files;
    }

    public Object getFileSystemObject() {
        return fileSystemObject;
    }

    public AdaptableList getFolders() {
        if (folders == null) {
            folders = new AdaptableList(0);
        }
        return folders;
    }

    public FileSystemElement getParent() {
        return this.parent;
    }

    public boolean isDirectory() {
        return isDirectory;
    }

    public void removeFolder(FileSystemElement child) {
        if (folders == null) {
			return;
		}
        folders.remove(child);
        child.setParent(null);
    }

    public void setFileSystemObject(Object value) {
        fileSystemObject = value;
    }

    public void setParent(FileSystemElement element) {
        parent = element;
    }

    @Override
	public String toString() {
        StringBuffer buf = new StringBuffer();
        if (isDirectory()) {
            buf.append("Folder(");//$NON-NLS-1$
        } else {
            buf.append("File(");//$NON-NLS-1$
        }
        buf.append(name).append(")");//$NON-NLS-1$
        if (!isDirectory()) {
            return buf.toString();
        }
        buf.append(" folders: ");//$NON-NLS-1$
        buf.append(folders);
        buf.append(" files: ");//$NON-NLS-1$
        buf.append(files);
        return buf.toString();
    }
}

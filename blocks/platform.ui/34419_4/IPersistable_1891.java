package org.eclipse.ui;

import org.eclipse.core.runtime.IPath;

public interface IPathEditorInput extends IEditorInput {
    public IPath getPath();
}

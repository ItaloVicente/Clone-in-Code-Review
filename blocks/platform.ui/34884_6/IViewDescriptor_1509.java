package org.eclipse.ui.views;

import org.eclipse.core.runtime.IPath;

public interface IViewCategory {

    IViewDescriptor[] getViews();

    String getId();

    String getLabel();

    IPath getPath();
}

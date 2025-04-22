package org.eclipse.ui.model;

import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.RGB;

public interface IWorkbenchAdapter2 {

    public RGB getForeground(Object element);

    public RGB getBackground(Object element);

    public FontData getFont(Object element);

}

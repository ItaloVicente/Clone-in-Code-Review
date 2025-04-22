package org.eclipse.ui.internal.part;

import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IPersistable;

public abstract class Part implements IPersistable
{
    
    public abstract Control getControl();

}

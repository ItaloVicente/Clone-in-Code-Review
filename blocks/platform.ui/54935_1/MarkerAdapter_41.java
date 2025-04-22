package org.eclipse.ui.views.markers.internal;

import java.util.Collection;

public interface ITableListener {
    public void contentsFound(Collection contents);

    public void invalid();

    public void drawable();
}

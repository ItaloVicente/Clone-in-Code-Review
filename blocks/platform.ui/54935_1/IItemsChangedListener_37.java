
package org.eclipse.ui.views.markers.internal;

public interface IFilter {

    public Object[] filter(Object[] elements);

    public boolean select(Object item);

}

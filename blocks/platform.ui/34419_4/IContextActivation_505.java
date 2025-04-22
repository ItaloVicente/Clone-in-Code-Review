
package org.eclipse.ui.contexts;

@Deprecated
public interface IContext extends Comparable {

    void addContextListener(IContextListener contextListener);

    String getId();

    String getName() throws NotDefinedException;

    String getParentId() throws NotDefinedException;

    boolean isDefined();

    boolean isEnabled();

    void removeContextListener(IContextListener contextListener);
}

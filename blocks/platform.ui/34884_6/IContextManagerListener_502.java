
package org.eclipse.ui.contexts;

import java.util.SortedSet;

@Deprecated
public interface IContextManager {

    void addContextManagerListener(
            IContextManagerListener contextManagerListener);

    IContext getContext(String contextId);

    SortedSet getDefinedContextIds();

    SortedSet getEnabledContextIds();

    void removeContextManagerListener(
            IContextManagerListener contextManagerListener);
}

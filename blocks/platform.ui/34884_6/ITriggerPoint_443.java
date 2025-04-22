
package org.eclipse.ui.activities;

import java.util.Set;

public interface IMutableActivityManager extends IActivityManager {

    void setEnabledActivityIds(Set enabledActivityIds);
}

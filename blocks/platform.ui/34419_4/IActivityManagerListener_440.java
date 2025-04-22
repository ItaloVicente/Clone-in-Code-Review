
package org.eclipse.ui.activities;

import java.util.Set;

public interface IActivityManager {

    void addActivityManagerListener(
            IActivityManagerListener activityManagerListener);

    IActivity getActivity(String activityId);

    ICategory getCategory(String categoryId);

    Set getDefinedActivityIds();

    Set getDefinedCategoryIds();

    Set getEnabledActivityIds();

    IIdentifier getIdentifier(String identifierId);

    void removeActivityManagerListener(
            IActivityManagerListener activityManagerListener);
}

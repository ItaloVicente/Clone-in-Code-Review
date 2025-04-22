
package org.eclipse.ui.internal.activities;

import java.util.List;

public interface IActivityRegistry {

    void addActivityRegistryListener(
            IActivityRegistryListener activityRegistryListener);

    List getActivityRequirementBindingDefinitions();

    List getActivityDefinitions();

    List getActivityPatternBindingDefinitions();

    List getCategoryActivityBindingDefinitions();

    List getCategoryDefinitions();

    List getDefaultEnabledActivities();

    void removeActivityRegistryListener(
            IActivityRegistryListener activityRegistryListener);
}

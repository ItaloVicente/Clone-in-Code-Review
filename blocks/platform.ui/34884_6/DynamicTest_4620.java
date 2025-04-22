package org.eclipse.ui.tests.activities;

import java.util.ArrayList;
import java.util.Iterator;

import org.eclipse.ui.internal.activities.AbstractActivityRegistry;
import org.eclipse.ui.internal.activities.ActivityDefinition;
import org.eclipse.ui.internal.activities.ActivityPatternBindingDefinition;
import org.eclipse.ui.internal.activities.ActivityRequirementBindingDefinition;
import org.eclipse.ui.internal.activities.CategoryActivityBindingDefinition;
import org.eclipse.ui.internal.activities.CategoryDefinition;

public class DynamicModelActivityRegistry extends AbstractActivityRegistry {
    final String sourceId = "org.eclipse.ui.tests"; //$NON-NLS-1$

    public DynamicModelActivityRegistry() {
        super();
        load();
    }

    private void load() {
        categoryDefinitions = new ArrayList();
        activityDefinitions = new ArrayList();
        categoryActivityBindingDefinitions = new ArrayList();
        activityPatternBindingDefinitions = new ArrayList();
        activityRequirementBindingDefinitions = new ArrayList();
        defaultEnabledActivities = new ArrayList();
        populateCategoryDefinitions();
        populateActivityDefinitions();
        populateCategoryActivityBindingDefinitions();
        populateActivityPatternBindingDefinitions();
        populateActivityRequirementBindingDefinitions();
        populateDefaultEnabledActivities();
    }

    private void populateDefaultEnabledActivities() {
        defaultEnabledActivities.add(((ActivityDefinition) activityDefinitions
                .toArray()[0]).getId());
        defaultEnabledActivities.add(((ActivityDefinition) activityDefinitions
                .toArray()[2]).getId());
        defaultEnabledActivities.add(((ActivityDefinition) activityDefinitions
                .toArray()[8]).getId());
    }

    private void populateActivityRequirementBindingDefinitions() {
        activityRequirementBindingDefinitions
                .add(new ActivityRequirementBindingDefinition(
                        ((ActivityDefinition) activityDefinitions.toArray()[0])
                                .getId(),
                        ((ActivityDefinition) activityDefinitions.toArray()[1])
                                .getId(), sourceId)); //$NON-NLS-1$
        activityRequirementBindingDefinitions
                .add(new ActivityRequirementBindingDefinition(
                        ((ActivityDefinition) activityDefinitions.toArray()[2])
                                .getId(),
                        ((ActivityDefinition) activityDefinitions.toArray()[3])
                                .getId(), sourceId)); //$NON-NLS-1$
    }

    private void populateActivityPatternBindingDefinitions() {
        for (int index = 0; index < activityDefinitions.size(); index++) {
            activityPatternBindingDefinitions
                    .add(new ActivityPatternBindingDefinition(
                            ((ActivityDefinition) activityDefinitions.toArray()[index])
                                    .getId(), "org.eclipse.pattern" //$NON-NLS-1$
                                    + Integer.toString(index + 1), sourceId));
        }
    }

    private void populateCategoryActivityBindingDefinitions() {
        int counter = 1;
        for (int index = 1; index <= categoryDefinitions.size(); index++) {
            categoryActivityBindingDefinitions
                    .add(new CategoryActivityBindingDefinition(
                            "org.eclipse.activity" + Integer.toString(counter), //$NON-NLS-1$
                            "org.eclipse.category" + Integer.toString(index), //$NON-NLS-1$
                            sourceId));
            counter++;
            categoryActivityBindingDefinitions
                    .add(new CategoryActivityBindingDefinition(
                            "org.eclipse.activity" + Integer.toString(counter), //$NON-NLS-1$
                            "org.eclipse.category" + Integer.toString(index), //$NON-NLS-1$
                            sourceId));
            counter++;
            categoryActivityBindingDefinitions
                    .add(new CategoryActivityBindingDefinition(
                            "org.eclipse.activity" + Integer.toString(counter), //$NON-NLS-1$
                            "org.eclipse.category" + Integer.toString(index), //$NON-NLS-1$
                            sourceId));
            counter++;
        }
    }

    private void populateActivityDefinitions() {
        String stringToAppend = null;
        for (int index = 1; index <= categoryDefinitions.size() * 3; index++) {
            stringToAppend = Integer.toString(index);
            activityDefinitions.add(new ActivityDefinition(
                    "org.eclipse.activity" + stringToAppend, "Activity " //$NON-NLS-1$ //$NON-NLS-2$
                            + stringToAppend, sourceId, "description")); //$NON-NLS-1$
        }
    }

    private void populateCategoryDefinitions() {
        String stringToAppend = null;
        for (int index = 1; index <= 6; index++) {
            stringToAppend = Integer.toString(index);
            categoryDefinitions.add(new CategoryDefinition(
                    "org.eclipse.category" + stringToAppend, "Category " //$NON-NLS-1$ //$NON-NLS-2$
                            + stringToAppend, sourceId, "description")); //$NON-NLS-1$
        }
    }

    public void addActivity(String activityId, String activityName) {
        activityDefinitions.add(new ActivityDefinition(activityId,
                activityName, sourceId, "description")); //$NON-NLS-1$
        fireActivityRegistryChanged();
    }

    public void removeActivity(String activityId, String activityName) {
        activityDefinitions.remove(new ActivityDefinition(activityId,
                activityName, sourceId, "description")); //$NON-NLS-1$
        fireActivityRegistryChanged();
    }

    public void addCategory(String categoryId, String categoryName) {
        categoryDefinitions.add(new CategoryDefinition(categoryId,
                categoryName, sourceId, "description")); //$NON-NLS-1$
        fireActivityRegistryChanged();
    }

    public void removeCategory(String categoryId, String categoryName) {
        categoryDefinitions.remove(new CategoryDefinition(categoryId,
                categoryName, sourceId, "description")); //$NON-NLS-1$
        fireActivityRegistryChanged();
    }

    public void addActivityRequirementBinding(String childId, String parentId) {
        activityRequirementBindingDefinitions
                .add(new ActivityRequirementBindingDefinition(childId,
                        parentId, sourceId));
        fireActivityRegistryChanged();
    }

    public void removeActivityRequirementBinding(String childId, String parentId) {
        activityRequirementBindingDefinitions
                .remove(new ActivityRequirementBindingDefinition(childId,
                        parentId, sourceId));
        fireActivityRegistryChanged();
    }

    public void addCategoryActivityBinding(String activityId, String categoryId) {
        categoryActivityBindingDefinitions
                .add(new CategoryActivityBindingDefinition(activityId,
                        categoryId, sourceId));
        fireActivityRegistryChanged();
    }

    public void removeCategoryActivityBinding(String activityId,
            String categoryId) {
        categoryActivityBindingDefinitions
                .remove(new CategoryActivityBindingDefinition(activityId,
                        categoryId, sourceId));
        fireActivityRegistryChanged();
    }

    public void updateCategoryDescription(String categoryId,
            String categoryDescription) {
        CategoryDefinition currentCategory = null;
        for (Iterator i = categoryDefinitions.iterator(); i.hasNext();) {
            currentCategory = (CategoryDefinition) i.next();
            if (currentCategory.getId().equals(categoryId)) {
                categoryDefinitions.remove(currentCategory);
                categoryDefinitions.add(new CategoryDefinition(categoryId,
                        currentCategory.getName(), currentCategory
                                .getSourceId(), categoryDescription));
                fireActivityRegistryChanged();
                return;
            }
        }
    }

    public void updateActivityDescription(String activityId,
            String activityDescription) {
        ActivityDefinition currentActivity = null;
        for (Iterator i = activityDefinitions.iterator(); i.hasNext();) {
            currentActivity = (ActivityDefinition) i.next();
            if (currentActivity.getId().equals(activityId)) {
                activityDefinitions.remove(currentActivity);
                activityDefinitions.add(new ActivityDefinition(activityId,
                        currentActivity.getName(), currentActivity
                                .getSourceId(), activityDescription));
                fireActivityRegistryChanged();
                return;
            }
        }
    }

    public void updateActivityName(String activityId, String activityName) {
        ActivityDefinition currentActivity = null;
        for (Iterator i = activityDefinitions.iterator(); i.hasNext();) {
            currentActivity = (ActivityDefinition) i.next();
            if (currentActivity.getId().equals(activityId)) {
                activityDefinitions.remove(currentActivity);
                activityDefinitions.add(new ActivityDefinition(activityId,
                        activityName, currentActivity.getSourceId(),
                        currentActivity.getDescription()));
                fireActivityRegistryChanged();
                return;
            }
        }
    }

    public void updateCategoryName(String categoryId, String categoryName) {
        CategoryDefinition currentCategory = null;
        for (Iterator i = categoryDefinitions.iterator(); i.hasNext();) {
            currentCategory = (CategoryDefinition) i.next();
            if (currentCategory.getId().equals(categoryId)) {
                categoryDefinitions.remove(currentCategory);
                categoryDefinitions.add(new CategoryDefinition(categoryId,
                        categoryName, currentCategory.getSourceId(),
                        currentCategory.getDescription()));
                fireActivityRegistryChanged();
                return;
            }
        }
    }

    public void removeActivityPatternBinding(String pattern) {
        ActivityPatternBindingDefinition currentDefinition = null;
        for (Iterator i = activityPatternBindingDefinitions.iterator(); i
                .hasNext();) {
            currentDefinition = (ActivityPatternBindingDefinition) i.next();
            if (currentDefinition.getPattern().equals(pattern)) {
                activityPatternBindingDefinitions.remove(currentDefinition);
                fireActivityRegistryChanged();
                return;
            }
        }
    }

    public void addActivityPatternBinding(String activityId, String pattern) {
        if (activityPatternBindingDefinitions
                .add(new ActivityPatternBindingDefinition(activityId, pattern,
                        sourceId))) {
            fireActivityRegistryChanged();
            return;
        }
    }
    
    public void addDefaultEnabledActivity(String activityId) {
        if (defaultEnabledActivities.add(activityId)) {
            fireActivityRegistryChanged();
        }
    }
    
    
    public void removeDefaultEnabledActivity(String activityId) {
        if (defaultEnabledActivities.remove(activityId)) {
            fireActivityRegistryChanged();
        }
    }
}

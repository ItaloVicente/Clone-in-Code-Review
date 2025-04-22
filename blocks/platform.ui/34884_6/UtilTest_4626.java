package org.eclipse.ui.tests.activities;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.activities.IActivity;
import org.eclipse.ui.activities.IActivityManager;
import org.eclipse.ui.activities.IActivityPatternBinding;
import org.eclipse.ui.activities.ICategory;
import org.eclipse.ui.activities.IIdentifier;
import org.eclipse.ui.activities.NotDefinedException;
import org.eclipse.ui.internal.activities.ActivityRequirementBinding;
import org.eclipse.ui.internal.activities.CategoryActivityBinding;
import org.eclipse.ui.tests.harness.util.UITestCase;

public class StaticTest extends UITestCase {
    private IActivityManager activityManager;

    private List categoryIds;

    private List activityIds;

    private List patternActivityIds;

    public StaticTest(String testName) {
        super(testName);
        activityManager = PlatformUI.getWorkbench().getActivitySupport()
                .getActivityManager();
        populateIds();
    }

    private void populateIds() {
        int index = 0;
        categoryIds = new ArrayList();
        for (index = 1; index <= 6; index++)
            categoryIds.add("org.eclipse.category" + Integer.toString(index)); //$NON-NLS-1$
        activityIds = new ArrayList();
        for (index = 1; index <= 18; index++)
            activityIds.add("org.eclipse.activity" + Integer.toString(index)); //$NON-NLS-1$
        patternActivityIds = new ArrayList();
        for (index = 0; index < 3; index++)
            patternActivityIds.add(activityIds.toArray()[index]);
    }

    public void testActivityManager() {
        assertTrue(activityManager.getDefinedCategoryIds().containsAll(
                categoryIds));
        assertTrue(activityManager.getDefinedActivityIds().containsAll(
                activityIds));
        for (int index = 1; index <= 4; index++)
            assertTrue(activityManager.getEnabledActivityIds().contains(
                    "org.eclipse.activity" + Integer.toString(index)));
        IIdentifier activityIdentifier = activityManager
                .getIdentifier("org.eclipse.pattern1");
        Set activityIds = activityIdentifier.getActivityIds();
        assertTrue(activityIds.containsAll(patternActivityIds));
        assertTrue(activityIdentifier.getId().equals("org.eclipse.pattern1"));
    }

    public void testActivity() {
        IActivity first_activity = activityManager
                .getActivity((String) activityIds.toArray()[0]);
        Set activityRequirementBindings = first_activity
                .getActivityRequirementBindings();
        for (int index = 2; index <= 7; index++) {
            assertTrue(activityRequirementBindings
                    .contains(new ActivityRequirementBinding(
                            "org.eclipse.activity" + Integer.toString(index),
                            "org.eclipse.activity1")));
        }
        Set activityPatternBindings = first_activity
                .getActivityPatternBindings();
        assertTrue(activityPatternBindings.size() != 0);
        IActivityPatternBinding activityPatternBinding = (IActivityPatternBinding) activityPatternBindings
                .toArray()[0];
        assertTrue(activityPatternBinding.getActivityId().equals(
                first_activity.getId()));
        assertTrue(activityPatternBinding.getPattern().pattern().equals(
                "org.eclipse.pattern1"));
        try {
            assertTrue(first_activity.getDescription().equals("description"));
        } catch (NotDefinedException e) {
            e.printStackTrace();
        }
        assertTrue(first_activity.getId().equals("org.eclipse.activity1"));
        try {
            assertTrue(first_activity.getName().equals("Activity 1"));
        } catch (NotDefinedException e) {
            e.printStackTrace();
        }
    }

    public void testCategory() {
        ICategory first_category = activityManager
                .getCategory((String) categoryIds.toArray()[0]);
        Set categoryActivityBindings = first_category
                .getCategoryActivityBindings();
        for (int index = 1; index <= 4; index++)
            assertTrue(categoryActivityBindings
                    .contains(new CategoryActivityBinding(
                            "org.eclipse.activity" + Integer.toString(index),
                            first_category.getId())));
        try {
            assertTrue(first_category.getDescription().equals("description"));
        } catch (NotDefinedException e) {
            e.printStackTrace();
        }
        assertTrue(first_category.getId().equals("org.eclipse.category1"));
        try {
            assertTrue(first_category.getName().equals("Category 1"));
        } catch (NotDefinedException e) {
            e.printStackTrace();
        }
    }
}

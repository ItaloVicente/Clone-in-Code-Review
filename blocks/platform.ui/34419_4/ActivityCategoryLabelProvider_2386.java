package org.eclipse.ui.internal.activities.ws;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.ui.activities.IActivity;
import org.eclipse.ui.activities.IActivityManager;
import org.eclipse.ui.activities.IActivityRequirementBinding;
import org.eclipse.ui.activities.ICategory;
import org.eclipse.ui.internal.activities.InternalActivityHelper;

public class ActivityCategoryContentProvider implements ITreeContentProvider {

    private IActivityManager manager;

    @Override
	public void dispose() {
        manager = null;
    }

	private IActivity[] getCategoryActivities(ICategory category) {
		Set activityIds = InternalActivityHelper.getActivityIdsForCategory(
				manager, category);
		List categoryActivities = new ArrayList(activityIds.size());
		for (Iterator i = activityIds.iterator(); i.hasNext();) {
			String activityId = (String) i.next();
			categoryActivities.add(new CategorizedActivity(category, manager
					.getActivity(activityId)));

		}
		return (IActivity[]) categoryActivities
				.toArray(new IActivity[categoryActivities.size()]);
	}

	public Object[] getDuplicateCategoryActivities(
			CategorizedActivity categorizedActivity) {
		ArrayList duplicateCategorizedactivities = new ArrayList();
		Set categoryIds = manager.getDefinedCategoryIds();
		ICategory currentCategory = null;
		String currentActivityId = null;
		IActivity[] categoryActivities = null;
		String currentCategoryId = null;
		for (Iterator i = categoryIds.iterator(); i.hasNext();) {
			currentCategoryId = (String) i.next();
			if (!currentCategoryId.equals(categorizedActivity.getCategory()
					.getId())) {
				currentCategory = manager.getCategory(currentCategoryId);
				categoryActivities = getCategoryActivities(currentCategory);
				for (int index = 0; index < categoryActivities.length; index++) {
					currentActivityId = categoryActivities[index].getId();
					if (currentActivityId.equals(categorizedActivity
							.getActivity().getId())) {
						duplicateCategorizedactivities
								.add(new CategorizedActivity(currentCategory,
										manager.getActivity(currentActivityId)));
						break;
					}
				}

			}

		}
		return duplicateCategorizedactivities.toArray();
	}

	public Object[] getChildRequiredActivities(String activityId) {
		ArrayList childRequiredActivities = new ArrayList();
		IActivity activity = manager.getActivity(activityId);
		Set actvitiyRequirementBindings = activity
				.getActivityRequirementBindings();
		String requiredActivityId = null;
		IActivityRequirementBinding currentActivityRequirementBinding = null;
		Object[] currentCategoryIds = null;
		for (Iterator i = actvitiyRequirementBindings.iterator(); i.hasNext();) {
			currentActivityRequirementBinding = (IActivityRequirementBinding) i
					.next();
			requiredActivityId = currentActivityRequirementBinding
					.getRequiredActivityId();
			currentCategoryIds = getActivityCategories(requiredActivityId);
			for (int index = 0; index < currentCategoryIds.length; index++) {
				childRequiredActivities.add(new CategorizedActivity(manager
						.getCategory((String) currentCategoryIds[index]),
						manager.getActivity(requiredActivityId)));
			}

		}

		return childRequiredActivities.toArray();
	}

	public Object[] getParentRequiredActivities(String activityId) {
		ArrayList parentRequiredActivities = new ArrayList();
		Set definedActivities = manager.getDefinedActivityIds();
		String currentActivityId = null;
		Set activityRequirementBindings = null;
		IActivityRequirementBinding currentActivityRequirementBinding = null;
		Object[] currentCategoryIds = null;
		for (Iterator i = definedActivities.iterator(); i.hasNext();) {
			currentActivityId = (String) i.next();
			activityRequirementBindings = manager
					.getActivity(currentActivityId)
					.getActivityRequirementBindings();
			for (Iterator j = activityRequirementBindings.iterator(); j
					.hasNext();) {
				currentActivityRequirementBinding = (IActivityRequirementBinding) j
						.next();
				if (currentActivityRequirementBinding.getRequiredActivityId()
						.equals(activityId)) {
					currentCategoryIds = getActivityCategories(currentActivityId);
					for (int index = 0; index < currentCategoryIds.length; index++) {
						parentRequiredActivities
								.add(new CategorizedActivity(
										manager
												.getCategory((String) currentCategoryIds[index]),
										manager.getActivity(currentActivityId)));
					}
				}
			}

		}
		return parentRequiredActivities.toArray();
	}

	private Object[] getActivityCategories(String activityId) {
		ArrayList activityCategories = new ArrayList();
		Set categoryIds = manager.getDefinedCategoryIds();
		String currentCategoryId = null;
		IActivity[] categoryActivities = null;
		for (Iterator i = categoryIds.iterator(); i.hasNext();) {
			currentCategoryId = (String) i.next();
			categoryActivities = getCategoryActivities(manager
					.getCategory(currentCategoryId));
			for (int index = 0; index < categoryActivities.length; index++) {
				if (categoryActivities[index].getId().equals(activityId)) {
					activityCategories.add(currentCategoryId);
					break;
				}
			}
		}
		return activityCategories.toArray();
	}

    @Override
	public Object[] getChildren(Object parentElement) {
        if (parentElement instanceof IActivityManager) {
            Set categoryIds = manager.getDefinedCategoryIds();
            ArrayList categories = new ArrayList(categoryIds.size());
            for (Iterator i = categoryIds.iterator(); i.hasNext();) {
                String categoryId = (String) i.next();
                ICategory category = manager.getCategory(categoryId);
				if (getCategoryActivities(category).length > 0) {
					categories.add(category);
				}
            }
            return categories.toArray();
        } else if (parentElement instanceof ICategory) {
            return getCategoryActivities((ICategory) parentElement);
        }
        return new Object[0];
    }

    @Override
	public Object[] getElements(Object inputElement) {
        return getChildren(inputElement);
    }

    @Override
	public Object getParent(Object element) {
        if (element instanceof CategorizedActivity) {
            return ((CategorizedActivity) element).getCategory();
        }
        return null;
    }

    @Override
	public boolean hasChildren(Object element) {
        if (element instanceof IActivityManager || element instanceof ICategory) {
			return true;
		}
        return false;
    }

    @Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
        manager = (IActivityManager) newInput;
    }
}

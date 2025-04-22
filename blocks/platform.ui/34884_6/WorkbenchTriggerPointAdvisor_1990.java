package org.eclipse.ui.activities;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.eclipse.ui.IPluginContribution;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.activities.ws.WorkbenchActivitySupport;

public final class WorkbenchActivityHelper {
	public static final String TRIGGER_PRE_UI_POINT = "org.eclipse.ui.workbenchModel"; //$NON-NLS-1$
	
	private static ITriggerPoint getTriggerPoint(String id) {
		return PlatformUI.getWorkbench().getActivitySupport()
				.getTriggerPointManager().getTriggerPoint(id);
	}

	public static IIdentifier getIdentifier(IPluginContribution contribution) {
		IWorkbenchActivitySupport workbenchActivitySupport = PlatformUI
				.getWorkbench().getActivitySupport();
		IIdentifier identifier = workbenchActivitySupport.getActivityManager()
				.getIdentifier(createUnifiedId(contribution));
		return identifier;
	}

	@Deprecated
	public static boolean allowUseOf(Object object) {
		return allowUseOf(PlatformUI.getWorkbench().getActivitySupport()
				.getTriggerPointManager().getTriggerPoint(
						ITriggerPointManager.UNKNOWN_TRIGGER_POINT_ID), object);
	}

	public static boolean allowUseOf(ITriggerPoint triggerPoint, Object object) {
		if (!isFiltering()) {
			return true;
		}
		if (triggerPoint == null) {
			return true;
		}
		if (object instanceof IPluginContribution) {
			IPluginContribution contribution = (IPluginContribution) object;
			IIdentifier identifier = getIdentifier(contribution);
			return allow(triggerPoint, identifier);
		}
		return true;
	}
	
	public static boolean restrictUseOf(Object object) {
		return !allowUseOf(getTriggerPoint(TRIGGER_PRE_UI_POINT), object);
	}

	private static boolean allow(ITriggerPoint triggerPoint,
			IIdentifier identifier) {
		if (identifier.isEnabled()) {
			return true;
		}

		ITriggerPointAdvisor advisor = ((WorkbenchActivitySupport) PlatformUI
				.getWorkbench().getActivitySupport()).getTriggerPointAdvisor();
		Set<?> activitiesToEnable = advisor.allow(triggerPoint, identifier);
		
		if (activitiesToEnable == null) {
			return false;
		}
		
		if (activitiesToEnable.isEmpty()) {
			return true;
		}

		enableActivities(activitiesToEnable);
		Set<?> newEnabled = PlatformUI.getWorkbench().getActivitySupport().getActivityManager()
				.getEnabledActivityIds();
		return newEnabled.containsAll(activitiesToEnable);
	}

	public static final String createUnifiedId(IPluginContribution contribution) {
		final String pluginId = contribution.getPluginId();
		if (pluginId != null) {
			return pluginId + '/' + contribution.getLocalId();
		}
		return contribution.getLocalId();
	}

	private static void enableActivities(Collection activities) {
		IWorkbenchActivitySupport activitySupport = PlatformUI.getWorkbench()
				.getActivitySupport();
		Set<?> newSet = new HashSet(activitySupport.getActivityManager().getEnabledActivityIds());
		newSet.addAll(activities);
		activitySupport.setEnabledActivityIds(newSet);
	}

	public static final boolean filterItem(Object object) {
		if (object instanceof IPluginContribution) {
			IPluginContribution contribution = (IPluginContribution) object;
			IWorkbenchActivitySupport workbenchActivitySupport = PlatformUI
					.getWorkbench().getActivitySupport();
			IIdentifier identifier = workbenchActivitySupport
					.getActivityManager().getIdentifier(
							createUnifiedId(contribution));
			if (!identifier.isEnabled()) {
				return true;
			}
		}
		return false;
	}

	public static final boolean isFiltering() {
		return !PlatformUI.getWorkbench().getActivitySupport()
				.getActivityManager().getDefinedActivityIds().isEmpty();
	}

	public static Set getEnabledCategories(IActivityManager activityManager,
			String categoryId) {
		ICategory category = activityManager.getCategory(categoryId);
		if (!category.isDefined()) {
			return Collections.EMPTY_SET;
		}

		Set<?> activities = expandActivityDependencies(getActivityIdsForCategory(category));
		Set<String> otherEnabledCategories = new HashSet<String>();
		Set<?> definedCategoryIds = activityManager.getDefinedCategoryIds();
		for (Iterator<?> i = definedCategoryIds.iterator(); i.hasNext();) {
			String otherCategoryId = (String) i.next();
			if (otherCategoryId.equals(categoryId)) {
				continue;
			}
			ICategory otherCategory = activityManager
					.getCategory(otherCategoryId);
			Set<?> otherCategoryActivityIds = expandActivityDependencies(getActivityIdsForCategory(otherCategory));
			if (activityManager.getEnabledActivityIds().containsAll(
					otherCategoryActivityIds)) {
				continue;
			}
			if (activities.containsAll(otherCategoryActivityIds)) {
				otherEnabledCategories.add(otherCategoryId);
			}

		}
		return otherEnabledCategories;
	}

	public static Set expandActivityDependencies(Set baseActivities) {
		Set<Object> extendedActivities = new HashSet<Object>();
		for (Iterator<?> i = baseActivities.iterator(); i.hasNext();) {
			String activityId = (String) i.next();
			Set<?> requiredActivities = getRequiredActivityIds(activityId);
			extendedActivities.addAll(requiredActivities);
		}
		extendedActivities.addAll(baseActivities);
		return extendedActivities;
	}

	public static Set getRequiredActivityIds(String activityId) {
		IActivityManager manager = PlatformUI.getWorkbench()
				.getActivitySupport().getActivityManager();
		IActivity activity = manager.getActivity(activityId);
		if (!activity.isDefined()) {
			return Collections.EMPTY_SET;
		}
		Set<?> requirementBindings = activity.getActivityRequirementBindings();
		if (requirementBindings.isEmpty()) {
			return Collections.EMPTY_SET;
		}

		Set<Object> requiredActivities = new HashSet<Object>(3);
		for (Iterator<?> i = requirementBindings.iterator(); i.hasNext();) {
			IActivityRequirementBinding binding = (IActivityRequirementBinding) i
					.next();
			requiredActivities.add(binding.getRequiredActivityId());
			requiredActivities.addAll(getRequiredActivityIds(binding.getRequiredActivityId()));
		}
		return requiredActivities;
	}

	public static Set getActivityIdsForCategory(ICategory category) {
		Set<?> bindings = category.getCategoryActivityBindings();
		Set<String> activityIds = new HashSet<String>();
		for (Iterator<?> i = bindings.iterator(); i.hasNext();) {
			ICategoryActivityBinding binding = (ICategoryActivityBinding) i
					.next();
			activityIds.add(binding.getActivityId());
		}
		return activityIds;
	}

	public static Set getDisabledCategories(IActivityManager activityManager,
			String categoryId) {
		ICategory category = activityManager.getCategory(categoryId);
		if (!category.isDefined()) {
			return Collections.EMPTY_SET;
		}

		Set<?> activities = expandActivityDependencies(getActivityIdsForCategory(category));
		Set<String> otherDisabledCategories = new HashSet<String>();
		Set<?> definedCategoryIds = activityManager.getDefinedCategoryIds();
		for (Iterator<?> i = definedCategoryIds.iterator(); i.hasNext();) {
			String otherCategoryId = (String) i.next();
			if (otherCategoryId.equals(categoryId)) {
				continue;
			}
			ICategory otherCategory = activityManager
					.getCategory(otherCategoryId);
			Set<?> otherCategoryActivityIds = expandActivityDependencies(getActivityIdsForCategory(otherCategory));

			if (otherCategoryActivityIds.isEmpty()) {
				continue;
			}

			if (!activities.containsAll(otherCategoryActivityIds)) {
				continue;
			}

			if (activityManager.getEnabledActivityIds().containsAll(
					otherCategoryActivityIds)) {
				otherDisabledCategories.add(otherCategoryId);
			}

		}
		return otherDisabledCategories;
	}

	public static final Set getContainedCategories(
			IActivityManager activityManager, String categoryId) {
		ICategory category = activityManager.getCategory(categoryId);
		if (!category.isDefined()) {
			return Collections.EMPTY_SET;
		}

		Set<?> activities = expandActivityDependencies(getActivityIdsForCategory(category));
		Set<String> containedCategories = new HashSet<String>();
		Set<?> definedCategoryIds = activityManager.getDefinedCategoryIds();
		for (Iterator<?> i = definedCategoryIds.iterator(); i.hasNext();) {
			String otherCategoryId = (String) i.next();
			if (otherCategoryId.equals(categoryId)) {
				continue;
			}
			ICategory otherCategory = activityManager
					.getCategory(otherCategoryId);
			Set<?> otherCategoryActivityIds = expandActivityDependencies(getActivityIdsForCategory(otherCategory));

			if (otherCategoryActivityIds.isEmpty()) {
				continue;
			}

			if (activities.containsAll(otherCategoryActivityIds)) {
				containedCategories.add(otherCategoryId);
			}

		}
		return containedCategories;

	}

	public static Set getEnabledCategories(IActivityManager activityManager) {

		Set<?> definedCategoryIds = activityManager.getDefinedCategoryIds();
		Set<String> enabledCategories = new HashSet<String>();
		for (Iterator<?> i = definedCategoryIds.iterator(); i.hasNext();) {
			String categoryId = (String) i.next();
			if (isEnabled(activityManager, categoryId)) {
				enabledCategories.add(categoryId);
			}
		}
		return enabledCategories;
	}
	
	public static Set getPartiallyEnabledCategories(
			IActivityManager activityManager) {
		Set<?> definedCategoryIds = activityManager.getDefinedCategoryIds();
		Set<String> partialCategories = new HashSet<String>();
		for (Iterator<?> i = definedCategoryIds.iterator(); i.hasNext();) {
			String categoryId = (String) i.next();
			if (isPartiallyEnabled(activityManager, categoryId)) {
				partialCategories.add(categoryId);
			}
		}

		return partialCategories;
	}

	public static boolean isPartiallyEnabled(IActivityManager activityManager,
			String categoryId) {
		Set<?> activityIds = getActivityIdsForCategory(activityManager.getCategory(categoryId));
		int foundCount = 0;
		for (Iterator<?> i = activityIds.iterator(); i.hasNext();) {
			String activityId = (String) i.next();
			if (activityManager.getEnabledActivityIds().contains(activityId)) {
				foundCount++;
			}
		}

		return foundCount > 0 && foundCount != activityIds.size();
	}

	public static Set getEnabledCategoriesForActivity(
			IActivityManager activityManager, String activityId) {
		Set<String> enabledCategoriesForActivity = new HashSet<String>();
		Set<?> enabledCategories = getEnabledCategories(activityManager);
		for (Iterator<?> i = enabledCategories.iterator(); i.hasNext();) {
			String categoryId = (String) i.next();
			if (getActivityIdsForCategory(
					activityManager.getCategory(categoryId)).contains(
					activityId)) {
				enabledCategoriesForActivity.add(categoryId);
			}
		}
		return enabledCategoriesForActivity;
	}

	public static boolean isEnabled(IActivityManager activityManager,
			String categoryId) {

		ICategory category = activityManager.getCategory(categoryId);
		if (category.isDefined()) {
			Set<?> activityIds = getActivityIdsForCategory(category);
			if (activityManager.getEnabledActivityIds().containsAll(activityIds)) {
				return true;
			}
		}

		return false;
	}

	public static ICategory[] resolveCategories(
			IMutableActivityManager activityManager, Set categoryIds) {
		ICategory[] categories = new ICategory[categoryIds.size()];
		String[] categoryIdArray = (String[]) categoryIds.toArray(new String[categoryIds.size()]);
		for (int i = 0; i < categoryIdArray.length; i++) {
			categories[i] = activityManager.getCategory(categoryIdArray[i]);
		}
		return categories;
	}
	
	public static Collection restrictCollection(Collection toBeFiltered, Collection result) {
		for (Iterator<?> iterator = toBeFiltered.iterator(); iterator.hasNext();) {
			Object item = iterator.next();
			if (!restrictUseOf(item)) {
				result.add(item);
			}
		}
		return result;
	}
	
	public static Object[] restrictArray(Object[] array) {
		ArrayList<Object> list = new ArrayList<Object>(array.length);
		for (int i = 0; i < array.length; i++) {
			if (!restrictUseOf(array[i])) {
				list.add(array[i]);
			}
		}
		return list.toArray((Object[]) Array.newInstance(array.getClass()
				.getComponentType(), list.size()));
	}
	
	public static Collection filterCollection(Collection toBeFiltered, Collection result) {
		for (Iterator<?> iterator = toBeFiltered.iterator(); iterator.hasNext();) {
			Object item = iterator.next();
			if (!filterItem(item)) {
				result.add(item);
			}
		}
		return result;
	}
	
	public static Object[] filterArray(Object[] array) {
		ArrayList<Object> list = new ArrayList<Object>(array.length);
		for (int i = 0; i < array.length; i++) {
			if (!filterItem(array[i])) {
				list.add(array[i]);
			}
		}
		return list.toArray((Object[]) Array.newInstance(array.getClass()
				.getComponentType(), list.size()));
	}

	private WorkbenchActivityHelper() {
	}
}

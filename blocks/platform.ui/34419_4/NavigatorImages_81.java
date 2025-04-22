
package org.eclipse.ui.internal.navigator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.SafeRunner;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.ui.internal.navigator.filters.CommonFilterDescriptor;
import org.eclipse.ui.internal.navigator.filters.CommonFilterDescriptorManager;
import org.eclipse.ui.internal.navigator.filters.SkeletonViewerFilter;
import org.eclipse.ui.navigator.ICommonFilterDescriptor;
import org.eclipse.ui.navigator.INavigatorFilterService;

public class NavigatorFilterService implements INavigatorFilterService {

	private static final ViewerFilter[] NO_FILTERS = new ViewerFilter[0];

	private static final String ACTIVATION_KEY = ".filterActivation"; //$NON-NLS-1$

	private static final String DELIM = ":"; //$NON-NLS-1$

	private final NavigatorContentService contentService;

	private final Map<ICommonFilterDescriptor, ViewerFilter> declaredViewerFilters = new HashMap<ICommonFilterDescriptor, ViewerFilter>();

	private final Set enforcedViewerFilters = new HashSet();

	private final Set<String> activeFilters = new HashSet<String>();

	public NavigatorFilterService(NavigatorContentService aContentService) {
		contentService = aContentService;
		restoreFilterActivation();
	}

	private synchronized void restoreFilterActivation() {
		SafeRunner.run(new NavigatorSafeRunnable() {
			@Override
			public void run() throws Exception {
				IEclipsePreferences prefs = NavigatorContentService.getPreferencesRoot();

				if (prefs.get(getFilterActivationPreferenceKey(), null) != null) {
					String activatedFiltersPreferenceValue = prefs.get(
							getFilterActivationPreferenceKey(), null);
					String[] activeFilterIds = activatedFiltersPreferenceValue.split(DELIM);
					for (int i = 0; i < activeFilterIds.length; i++) {
						activeFilters.add(activeFilterIds[i]);
					}

				} else {
					ICommonFilterDescriptor[] visibleFilterDescriptors = getVisibleFilterDescriptors();
					for (int i = 0; i < visibleFilterDescriptors.length; i++) {
						if (visibleFilterDescriptors[i].isActiveByDefault()) {
							activeFilters.add(visibleFilterDescriptors[i].getId());
						}
					}
				}
			}
		});
	}

	@Override
	public void persistFilterActivationState() {

		synchronized (activeFilters) {
			CommonFilterDescriptorManager dm = CommonFilterDescriptorManager
			.getInstance();

			StringBuffer activatedFiltersPreferenceValue = new StringBuffer(DELIM);

			for (Iterator<String> activeItr = activeFilters.iterator(); activeItr.hasNext();) {
				String id = activeItr.next().toString();
				if (!dm.getFilterById(id).isVisibleInUi())
					continue;
				activatedFiltersPreferenceValue.append(id).append(DELIM);
			}

			IEclipsePreferences prefs = NavigatorContentService.getPreferencesRoot();
			prefs.put(getFilterActivationPreferenceKey(), activatedFiltersPreferenceValue.toString());
			NavigatorContentService.flushPreferences(prefs);
		}
	}
	
	public void resetFilterActivationState() {
		IEclipsePreferences prefs = NavigatorContentService.getPreferencesRoot();
		prefs.remove(getFilterActivationPreferenceKey());
		NavigatorContentService.flushPreferences(prefs);
	}

	private String getFilterActivationPreferenceKey() {
		return contentService.getViewerId() + ACTIVATION_KEY;
	}

	@Override
	public ViewerFilter[] getVisibleFilters(boolean toReturnOnlyActiveFilters) {
		CommonFilterDescriptor[] descriptors = CommonFilterDescriptorManager
				.getInstance().findVisibleFilters(contentService);

		List<ViewerFilter> filters = new ArrayList<ViewerFilter>();

		ViewerFilter instance;
		for (int i = 0; i < descriptors.length; i++) {
			if (!toReturnOnlyActiveFilters || isActive(descriptors[i].getId())) {
				instance = getViewerFilter(descriptors[i]);
				if (instance != null) {
					filters.add(instance);
				}
			}
		}

		filters.addAll(enforcedViewerFilters);

		if (filters.size() == 0) {
			return NO_FILTERS;
		}
		return filters
				.toArray(new ViewerFilter[filters.size()]);
	}

	@Override
	public ViewerFilter getViewerFilter(ICommonFilterDescriptor descriptor) {
		ViewerFilter filter = null;
		synchronized (declaredViewerFilters) {
			filter = declaredViewerFilters.get(descriptor);
			if (filter == null) {
				declaredViewerFilters.put(descriptor,
						(filter = ((CommonFilterDescriptor) descriptor)
								.createFilter()));
			}
		}
		return filter;
	}

	@Override
	public ICommonFilterDescriptor[] getVisibleFilterDescriptors() {
		return CommonFilterDescriptorManager.getInstance().findVisibleFilters(
				contentService);
	}

	public ICommonFilterDescriptor[] getVisibleFilterDescriptorsForUI() {
		return CommonFilterDescriptorManager.getInstance().findVisibleFilters(
				contentService, CommonFilterDescriptorManager.FOR_UI);
	}

	@Override
	public boolean isActive(String aFilterId) {
		synchronized (activeFilters) {
			return activeFilters.contains(aFilterId);
		}
	}

	@Override
	public void setActiveFilterIds(String[] theFilterIds) {
		Assert.isNotNull(theFilterIds);
		synchronized (activeFilters) {
			activeFilters.clear();
			activeFilters.addAll(Arrays.asList(theFilterIds));
		}
	}
	
	@Override
	public void activateFilterIdsAndUpdateViewer(String[] filterIdsToActivate) {
		boolean updateFilterActivation = false;

		Arrays.sort(filterIdsToActivate);
		CommonFilterDescriptor[] visibleFilterDescriptors = (CommonFilterDescriptor[]) getVisibleFilterDescriptors();

		int indexofFilterIdToBeActivated;

		List<String> nonUiVisible = null;
		
		for (int i = 0; i < visibleFilterDescriptors.length; i++) {
			indexofFilterIdToBeActivated = Arrays.binarySearch(filterIdsToActivate,
					visibleFilterDescriptors[i].getId());
			if (indexofFilterIdToBeActivated >= 0 ^ isActive(visibleFilterDescriptors[i].getId())) {
				updateFilterActivation = true;
			}
			
			if (!visibleFilterDescriptors[i].isVisibleInUi()) {
				if (nonUiVisible == null)
					nonUiVisible = new ArrayList<String>();
				nonUiVisible.add(visibleFilterDescriptors[i].getId());
			}
		}

		if (updateFilterActivation) {
			if (nonUiVisible != null) {
				for (int i = 0; i < filterIdsToActivate.length; i++)
					nonUiVisible.add(filterIdsToActivate[i]);
				filterIdsToActivate = nonUiVisible.toArray(new String[]{});
			}
			
			setActiveFilterIds(filterIdsToActivate);
			persistFilterActivationState();
			updateViewer();
			StructuredViewer commonViewer = (StructuredViewer) contentService.getViewer();
			commonViewer.setSelection(StructuredSelection.EMPTY);
		}
	}

	public void updateViewer() {
		StructuredViewer commonViewer = (StructuredViewer) contentService.getViewer();

		ViewerFilter[] visibleFilters =	getVisibleFilters(true);
		commonViewer.setFilters(visibleFilters);
	}		
		
	public void addActiveFilterIds(String[] theFilterIds) {
		Assert.isNotNull(theFilterIds);
		synchronized (activeFilters) {
			activeFilters.addAll(Arrays.asList(theFilterIds));
		}
	}
	
	public void setActive(String aFilterId, boolean toMakeActive) {

		synchronized (activeFilters) {
			boolean isActive = activeFilters.contains(aFilterId);
			if(isActive ^ toMakeActive) {
				if(toMakeActive)
					activeFilters.remove(aFilterId);
				else
					activeFilters.add(aFilterId);
					
			}
				
		}
	}
 
}

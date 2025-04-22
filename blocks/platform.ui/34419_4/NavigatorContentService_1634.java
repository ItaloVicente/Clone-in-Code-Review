package org.eclipse.ui.internal.navigator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.runtime.ListenerList;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.ui.internal.navigator.extensions.NavigatorContentDescriptor;
import org.eclipse.ui.internal.navigator.extensions.NavigatorContentDescriptorManager;
import org.eclipse.ui.navigator.IExtensionActivationListener;
import org.eclipse.ui.navigator.INavigatorActivationService;
import org.eclipse.ui.navigator.INavigatorContentDescriptor;
import org.eclipse.ui.navigator.INavigatorContentService;

public final class NavigatorActivationService implements
		INavigatorActivationService {

	private static final String ACTIVATED_EXTENSIONS = ".activatedExtensions"; //$NON-NLS-1$

	private static final NavigatorContentDescriptorManager CONTENT_DESCRIPTOR_REGISTRY = NavigatorContentDescriptorManager
			.getInstance();	

	private static final INavigatorContentDescriptor[] NO_DESCRIPTORS = new INavigatorContentDescriptor[0];

	private static final String DELIM = ";"; //$NON-NLS-1$

	private static final char EQUALS = '=';  

	private final Map<String, Boolean> activatedExtensionsMap = new HashMap<String, Boolean>();

	private final ListenerList listeners = new ListenerList();

	private INavigatorContentService contentService;

	public NavigatorActivationService(INavigatorContentService aContentService) {
		contentService = aContentService;
		revertExtensionActivations();
	}

	@Override
	public boolean isNavigatorExtensionActive(String aNavigatorExtensionId) {
		Boolean b = activatedExtensionsMap.get(aNavigatorExtensionId);
		if(b != null)
			return b.booleanValue();
		synchronized (activatedExtensionsMap) {
			NavigatorContentDescriptor descriptor = CONTENT_DESCRIPTOR_REGISTRY.getContentDescriptor(aNavigatorExtensionId);
			if (descriptor == null)
				return false;
			if(descriptor.isActiveByDefault())
				activatedExtensionsMap.put(aNavigatorExtensionId, Boolean.TRUE);
			else
				activatedExtensionsMap.put(aNavigatorExtensionId, Boolean.FALSE);
			return descriptor.isActiveByDefault();
		}
	}

	public void setActive(
			String aNavigatorExtensionId, boolean toEnable) {

		boolean currentlyActive = isNavigatorExtensionActive(aNavigatorExtensionId);
		if (currentlyActive == toEnable) {
			return;
		}

		if (toEnable) {
			activatedExtensionsMap.put(aNavigatorExtensionId, Boolean.TRUE);
		} else {
			activatedExtensionsMap.put(aNavigatorExtensionId, Boolean.FALSE);
		}
		notifyListeners(new String[] { aNavigatorExtensionId }, toEnable);

	}

	public void setActive(String[] aNavigatorExtensionIds,
			boolean toEnable) {

		if (toEnable) {
			for (int i = 0; i < aNavigatorExtensionIds.length; i++) {
				activatedExtensionsMap.put(aNavigatorExtensionIds[i], Boolean.TRUE);
			}
		} else {
			for (int i = 0; i < aNavigatorExtensionIds.length; i++) {
				activatedExtensionsMap.put(aNavigatorExtensionIds[i], Boolean.FALSE);
			}
		}
		notifyListeners(aNavigatorExtensionIds, toEnable);

	}

	@Override
	public void persistExtensionActivations() {
		IEclipsePreferences prefs = NavigatorContentService.getPreferencesRoot();
		
		synchronized (activatedExtensionsMap) {
			Iterator<String> activatedExtensionsIterator = activatedExtensionsMap.keySet().iterator();
			
			StringBuffer preferenceValue = new StringBuffer();
			String navigatorExtensionId = null;
			boolean isActive = false;
			while (activatedExtensionsIterator.hasNext()) {
				navigatorExtensionId = activatedExtensionsIterator.next();
				isActive = isNavigatorExtensionActive(navigatorExtensionId);
				preferenceValue.append(navigatorExtensionId)
									.append(EQUALS)
										.append( isActive ? Boolean.TRUE : Boolean.FALSE )				
											.append(DELIM);
			}
			prefs.put(getPreferenceKey(), preferenceValue.toString());
		}

		NavigatorContentService.flushPreferences(prefs);
	}

	@Override
	public void addExtensionActivationListener(
			IExtensionActivationListener aListener) {
		listeners.add(aListener);
	}

	@Override
	public void removeExtensionActivationListener(
			IExtensionActivationListener aListener) {
		listeners.remove(aListener);
	}

	private void notifyListeners(String[] navigatorExtensionIds,
			boolean toEnable) {
		
		if(navigatorExtensionIds != null) { // should really never be null, but just in case
			if(navigatorExtensionIds.length > 1)
				Arrays.sort(navigatorExtensionIds);
			
			Object[] listenerArray = listeners.getListeners();
			for (int i = 0; i < listenerArray.length; i++) {
				((IExtensionActivationListener) listenerArray[i])
						.onExtensionActivation(contentService.getViewerId(),
								navigatorExtensionIds, toEnable);
			}
		}

	}

	private void revertExtensionActivations() {

		IEclipsePreferences prefs = NavigatorContentService.getPreferencesRoot();

		String activatedExtensionsString = prefs
				.get(getPreferenceKey(), null);

		if (activatedExtensionsString != null
				&& activatedExtensionsString.length() > 0) {
			String[] contentExtensionIds = activatedExtensionsString
					.split(DELIM);
			
			String id = null;
			String booleanString = null;
			int indx=0;
			for (int i = 0; i < contentExtensionIds.length; i++) {
				if( (indx = contentExtensionIds[i].indexOf(EQUALS)) > -1) {
					id = contentExtensionIds[i].substring(0, indx);
					booleanString = contentExtensionIds[i].substring(indx+1, contentExtensionIds[i].length());
					activatedExtensionsMap.put(id, Boolean.valueOf(booleanString));
				} else {
					NavigatorContentDescriptor descriptor = CONTENT_DESCRIPTOR_REGISTRY.getContentDescriptor(contentExtensionIds[i]);
					if(descriptor != null)
						activatedExtensionsMap.put(id, Boolean.valueOf(descriptor.isActiveByDefault()));
				}
			}

		} else {
			INavigatorContentDescriptor[] contentDescriptors = CONTENT_DESCRIPTOR_REGISTRY
					.getAllContentDescriptors();
			for (int i = 0; i < contentDescriptors.length; i++) {
				if (contentDescriptors[i].isActiveByDefault()) {					
					activatedExtensionsMap.put(contentDescriptors[i].getId(), Boolean.TRUE);
				}
			}
		} 
	}

	private String getPreferenceKey() {
		return contentService.getViewerId() + ACTIVATED_EXTENSIONS;
	}


	@Override
	public INavigatorContentDescriptor[] activateExtensions(
			String[] extensionIds, boolean toDeactivateAllOthers) {

		Set<NavigatorContentDescriptor> activatedDescriptors = new HashSet<NavigatorContentDescriptor>(); 
		setActive(extensionIds, true);
		for (int extId = 0; extId < extensionIds.length; extId++) {
			activatedDescriptors.add(CONTENT_DESCRIPTOR_REGISTRY
					.getContentDescriptor(extensionIds[extId]));
		}

		if (toDeactivateAllOthers) {
			NavigatorContentDescriptor[] descriptors = CONTENT_DESCRIPTOR_REGISTRY
					.getAllContentDescriptors();
			List<NavigatorContentDescriptor> descriptorList = new ArrayList<NavigatorContentDescriptor>(Arrays.asList(descriptors));

			for (int descriptorIndx = 0; descriptorIndx < descriptors.length; descriptorIndx++) {
				for (int extId = 0; extId < extensionIds.length; extId++) {
					if (descriptors[descriptorIndx].getId().equals(
							extensionIds[extId])) {
						descriptorList.remove(descriptors[descriptorIndx]);
					}
				}
			}

			String[] deactivatedExtensions = new String[descriptorList.size()];
			for (int i = 0; i < descriptorList.size(); i++) {
				INavigatorContentDescriptor descriptor = descriptorList
						.get(i);
				deactivatedExtensions[i] = descriptor.getId();
			}
			setActive(deactivatedExtensions, false);
		}

		if (activatedDescriptors.size() == 0) {
			return NO_DESCRIPTORS;
		}
		return activatedDescriptors
				.toArray(new NavigatorContentDescriptor[activatedDescriptors
						.size()]);
	}

	@Override
	public INavigatorContentDescriptor[] deactivateExtensions(
			String[] extensionIds, boolean toEnableAllOthers) {

		Set<NavigatorContentDescriptor> activatedDescriptors = new HashSet<NavigatorContentDescriptor>(); 
		setActive(extensionIds, false);

		if (toEnableAllOthers) {
			NavigatorContentDescriptor[] descriptors = CONTENT_DESCRIPTOR_REGISTRY
					.getAllContentDescriptors();
			List<NavigatorContentDescriptor> descriptorList = new ArrayList<NavigatorContentDescriptor>(Arrays.asList(descriptors));

			for (int descriptorIndx = 0; descriptorIndx < descriptors.length; descriptorIndx++) {
				for (int extId = 0; extId < extensionIds.length; extId++) {
					if (descriptors[descriptorIndx].getId().equals(
							extensionIds[extId])) {
						descriptorList.remove(descriptors[descriptorIndx]);
					}
				}
			}

			String[] activatedExtensions = new String[descriptorList.size()];
			for (int i = 0; i < descriptorList.size(); i++) {
				NavigatorContentDescriptor descriptor = descriptorList
						.get(i);
				activatedExtensions[i] = descriptor.getId();
				activatedDescriptors.add(descriptor);
			}
			setActive(activatedExtensions,	true);
		}
		if (activatedDescriptors.size() == 0) {
			return NO_DESCRIPTORS;
		}

		return activatedDescriptors
				.toArray(new NavigatorContentDescriptor[activatedDescriptors
						.size()]);
	}


}

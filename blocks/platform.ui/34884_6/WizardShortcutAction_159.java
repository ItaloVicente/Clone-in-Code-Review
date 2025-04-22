package org.eclipse.ui.internal.navigator.wizards;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.SafeRunner;
import org.eclipse.ui.activities.WorkbenchActivityHelper;
import org.eclipse.ui.internal.navigator.NavigatorPlugin;
import org.eclipse.ui.internal.navigator.NavigatorSafeRunnable;
import org.eclipse.ui.internal.navigator.extensions.NavigatorContentRegistryReader;
import org.eclipse.ui.navigator.INavigatorContentService;

public class CommonWizardDescriptorManager {

	private static final CommonWizardDescriptorManager INSTANCE = new CommonWizardDescriptorManager();

	private static boolean isInitialized = false;

	private static final String[] NO_DESCRIPTOR_IDS = new String[0];

	private static final CommonWizardDescriptor[] NO_DESCRIPTORS = new CommonWizardDescriptor[0];
	
	public static final String WIZARD_TYPE_NEW = "new"; //$NON-NLS-1$

	private Map<String, Set> commonWizardDescriptors = new HashMap<String, Set>();

	public static CommonWizardDescriptorManager getInstance() {
		if (isInitialized) {
			return INSTANCE;
		}
		synchronized (INSTANCE) {
			if (!isInitialized) {
				INSTANCE.init();
				isInitialized = true;
			}
		}
		return INSTANCE;
	}

	private void init() {
		new CommonWizardRegistry().readRegistry();
	}

	private void addCommonWizardDescriptor(CommonWizardDescriptor aDesc) {
		if (aDesc == null) {
			return;
		} else if(aDesc.getWizardId() == null) {
			NavigatorPlugin.logError(0, "A null wizardId was supplied for a commonWizard in " + aDesc.getNamespace(), null); //$NON-NLS-1$
		}
		synchronized (commonWizardDescriptors) {
			Set<CommonWizardDescriptor> descriptors = commonWizardDescriptors.get(aDesc
					.getType());
			if (descriptors == null) { 
				commonWizardDescriptors.put(aDesc.getType(), descriptors = new HashSet<CommonWizardDescriptor>());
			}
			if (!descriptors.contains(aDesc)) {
				descriptors.add(aDesc);
			}
		}
	}

	public String[] getEnabledCommonWizardDescriptorIds(Object anElement,
			String aType, INavigatorContentService aContentService) {

		Set commonDescriptors = commonWizardDescriptors.get(aType);
		if (commonDescriptors == null) {
			return NO_DESCRIPTOR_IDS;
		}
		List<String> descriptorIds = new ArrayList<String>();
		for (Iterator commonWizardDescriptorsItr = commonDescriptors.iterator(); commonWizardDescriptorsItr
				.hasNext();) {
			CommonWizardDescriptor descriptor = (CommonWizardDescriptor) commonWizardDescriptorsItr
					.next();

			if (isVisible(aContentService, descriptor)
					&& descriptor.isEnabledFor(anElement)) {
				descriptorIds.add(descriptor.getWizardId());
			}
		}
		String[] wizardIds = new String[descriptorIds.size()];
		return descriptorIds.toArray(wizardIds); 
	}
	

	public CommonWizardDescriptor[] getEnabledCommonWizardDescriptors(Object anElement,
			String aType, INavigatorContentService aContentService) {

		Set commonDescriptors = commonWizardDescriptors.get(aType);
		if (commonDescriptors == null) {
			return NO_DESCRIPTORS;
		}
		List<CommonWizardDescriptor> descriptors = new ArrayList<CommonWizardDescriptor>();
		for (Iterator commonWizardDescriptorsItr = commonDescriptors.iterator(); commonWizardDescriptorsItr
				.hasNext();) {
			CommonWizardDescriptor descriptor = (CommonWizardDescriptor) commonWizardDescriptorsItr
					.next();

			if (isVisible(aContentService, descriptor)
					&& descriptor.isEnabledFor(anElement)) {
				descriptors.add(descriptor);
			}
		}
		CommonWizardDescriptor[] enabledDescriptors = new CommonWizardDescriptor[descriptors.size()];
		return descriptors.toArray(enabledDescriptors);  
	}

	private boolean isVisible(INavigatorContentService aContentService, CommonWizardDescriptor descriptor) {
		return !WorkbenchActivityHelper.filterItem(descriptor) && 
					(aContentService == null || 
							(descriptor.getId() == null || 
									( aContentService.isVisible(descriptor.getId()) && 
											aContentService.isActive(descriptor.getId())
									)
							)
					);
	}
  
	private class CommonWizardRegistry extends NavigatorContentRegistryReader {

		@Override
		protected boolean readElement(final IConfigurationElement anElement) {
			final boolean[] retValue = new boolean[1];

			if (TAG_COMMON_WIZARD.equals(anElement.getName())) {
				SafeRunner.run(new NavigatorSafeRunnable(anElement) {
					@Override
					public void run() throws Exception {
						addCommonWizardDescriptor(new CommonWizardDescriptor(anElement));
						retValue[0] = true;
					}
				});
				return retValue[0];
			}
			if (TAG_NAVIGATOR_CONTENT.equals(anElement.getName())) {
				IConfigurationElement[] commonWizards = anElement.getChildren(TAG_COMMON_WIZARD);
				final String contentExtensionId = anElement.getAttribute(ATT_ID);
				retValue[0] = true;
				for (int i = 0; i < commonWizards.length; i++) {
					final IConfigurationElement element = commonWizards[i];
					retValue[0] = false;
					SafeRunner.run(new NavigatorSafeRunnable(element) {
						@Override
						public void run() throws Exception {
							addCommonWizardDescriptor(new CommonWizardDescriptor(element,
									contentExtensionId));
							retValue[0] = true;
						}
					});
					if (!retValue[0])
						break;
				}
				return retValue[0];
			}
			return super.readElement(anElement);
		}
	}

}

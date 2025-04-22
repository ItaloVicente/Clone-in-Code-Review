package org.eclipse.ui.internal.activities.ws;

import java.util.ArrayList;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.WorkbenchPlugin;
import org.eclipse.ui.internal.misc.StatusUtil;
import org.eclipse.ui.internal.registry.IWorkbenchRegistryConstants;
import org.eclipse.ui.internal.registry.RegistryReader;

public class TriggerPointAdvisorRegistry {

	private static TriggerPointAdvisorRegistry instance;

	private TriggerPointAdvisorRegistry() {
	}

	public static TriggerPointAdvisorRegistry getInstance() {
		if (instance == null) {
			instance = new TriggerPointAdvisorRegistry();
		}

		return instance;
	}

	public TriggerPointAdvisorDescriptor[] getAdvisors() {
		IExtensionPoint point = Platform.getExtensionRegistry()
				.getExtensionPoint(PlatformUI.PLUGIN_ID,
						IWorkbenchRegistryConstants.PL_ACTIVITYSUPPORT);
		if (point == null) {
			return new TriggerPointAdvisorDescriptor[0];
		}

		IExtension[] extensions = point.getExtensions();
		extensions = RegistryReader.orderExtensions(extensions);

		ArrayList list = new ArrayList(extensions.length);
		for (int i = 0; i < extensions.length; i++) {
			IConfigurationElement[] elements = extensions[i]
					.getConfigurationElements();
			for (int j = 0; j < elements.length; j++) {
				if (elements[j].getName().equals(
						IWorkbenchRegistryConstants.TAG_TRIGGERPOINTADVISOR)) {
					try {
						TriggerPointAdvisorDescriptor descriptor = new TriggerPointAdvisorDescriptor(
								elements[j]);
						list.add(descriptor);
					} catch (IllegalArgumentException e) {
						WorkbenchPlugin.log(
								"invalid trigger point advisor extension", //$NON-NLS-1$
								StatusUtil.newStatus(IStatus.ERROR, e
										.getMessage(), e));
					}
				}
			}
		}

		return (TriggerPointAdvisorDescriptor[]) list
				.toArray(new TriggerPointAdvisorDescriptor[list.size()]);
	}

	public TriggerPointAdvisorDescriptor getAdvisorForProduct(String productId) {
		IExtensionPoint point = Platform.getExtensionRegistry()
				.getExtensionPoint(PlatformUI.PLUGIN_ID,
						IWorkbenchRegistryConstants.PL_ACTIVITYSUPPORT);
		if (point == null) {
			return null;
		}

		IExtension[] extensions = point.getExtensions();
		extensions = RegistryReader.orderExtensions(extensions);

		String targetIntroId = getAdvisorForProduct(productId, extensions);
		if (targetIntroId == null) {
			return null;
		}

		TriggerPointAdvisorDescriptor[] advisors = getAdvisors();
		for (int i = 0; i < advisors.length; i++) {
			if (advisors[i].getId().equals(targetIntroId)) {
				return advisors[i];
			}
		}

		return null;
	}

	private String getAdvisorForProduct(String targetProductId,
			IExtension[] extensions) {
		for (int i = 0; i < extensions.length; i++) {
			IConfigurationElement[] elements = extensions[i]
					.getConfigurationElements();
			for (int j = 0; j < elements.length; j++) {
				if (elements[j].getName().equals(
						IWorkbenchRegistryConstants.TAG_ADVISORPRODUCTBINDING)) {
					String advisorId = elements[j]
							.getAttribute(IWorkbenchRegistryConstants.ATT_ADVISORID);
					String productId = elements[j]
							.getAttribute(IWorkbenchRegistryConstants.ATT_PRODUCTID);

					if (advisorId == null || productId == null) {
						IStatus status = new Status(
								IStatus.ERROR,
								elements[j].getDeclaringExtension()
										.getNamespace(),
								IStatus.ERROR,
								"triggerPointAdvisorId and productId must be defined.", new IllegalArgumentException()); //$NON-NLS-1$
						WorkbenchPlugin
								.log(
										"Invalid trigger point advisor binding", status); //$NON-NLS-1$
						continue;
					}

					if (targetProductId.equals(productId)) {
						return advisorId;
					}
				}
			}
		}
		return null;
	}

}

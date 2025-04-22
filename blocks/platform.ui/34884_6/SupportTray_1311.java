
package org.eclipse.ui.internal.statushandlers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.dynamichelpers.ExtensionTracker;
import org.eclipse.core.runtime.dynamichelpers.IExtensionChangeHandler;
import org.eclipse.core.runtime.dynamichelpers.IExtensionTracker;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.WorkbenchPlugin;

public class StatusHandlerRegistry implements IExtensionChangeHandler {

	private static final String STATUSHANDLERS_POINT_NAME = "statusHandlers"; //$NON-NLS-1$

	private static final String TAG_STATUSHANDLER = "statusHandler"; //$NON-NLS-1$

	private static final String TAG_STATUSHANDLER_PRODUCTBINDING = "statusHandlerProductBinding"; //$NON-NLS-1$
	
	private static final String STATUSHANDLER_ARG = "-statushandler"; //$NON-NLS-1$

	private ArrayList statusHandlerDescriptors = new ArrayList();

	private ArrayList productBindingDescriptors = new ArrayList();

	private StatusHandlerDescriptorsMap statusHandlerDescriptorsMap;

	private StatusHandlerDescriptor defaultHandlerDescriptor;

	private static StatusHandlerRegistry instance;

	private StatusHandlerRegistry() {
		IExtensionTracker tracker = PlatformUI.getWorkbench()
				.getExtensionTracker();
		IExtensionPoint handlersPoint = Platform.getExtensionRegistry()
				.getExtensionPoint(WorkbenchPlugin.PI_WORKBENCH,
						STATUSHANDLERS_POINT_NAME);
		IExtension[] extensions = handlersPoint.getExtensions();

		statusHandlerDescriptorsMap = new StatusHandlerDescriptorsMap();

		for (int i = 0; i < extensions.length; i++) {
			addExtension(tracker, extensions[i]);
		}

		tracker.registerHandler(this, ExtensionTracker
				.createExtensionPointFilter(handlersPoint));

		IExtensionPoint productsPoint = Platform.getExtensionRegistry()
				.getExtensionPoint(Platform.PI_RUNTIME, Platform.PT_PRODUCT);

		tracker.registerHandler(this, ExtensionTracker
				.createExtensionPointFilter(productsPoint));
	}

	public static StatusHandlerRegistry getDefault() {
		if (instance == null) {
			instance = new StatusHandlerRegistry();
		}
		return instance;
	}

	@Override
	public void addExtension(IExtensionTracker tracker, IExtension extension) {
		IConfigurationElement[] configElements = extension
				.getConfigurationElements();
		for (int j = 0; j < configElements.length; j++) {
			if (configElements[j].getName().equals(TAG_STATUSHANDLER)) {
				StatusHandlerDescriptor descriptor = new StatusHandlerDescriptor(
						configElements[j]);
				tracker.registerObject(extension, descriptor,
						IExtensionTracker.REF_STRONG);
				statusHandlerDescriptors.add(descriptor);
			} else if (configElements[j].getName().equals(
					TAG_STATUSHANDLER_PRODUCTBINDING)) {
				StatusHandlerProductBindingDescriptor descriptor = new StatusHandlerProductBindingDescriptor(
						configElements[j]);
				tracker.registerObject(extension, descriptor,
						IExtensionTracker.REF_STRONG);
				productBindingDescriptors.add(descriptor);
			}
		}
		buildHandlersStructure();
	}

	@Override
	public void removeExtension(IExtension extension, Object[] objects) {
		for (int i = 0; i < objects.length; i++) {
			if (objects[i] instanceof StatusHandlerDescriptor) {
				statusHandlerDescriptors.remove(objects[i]);
			} else if (objects[i] instanceof StatusHandlerProductBindingDescriptor) {
				productBindingDescriptors.remove(objects[i]);
			}
		}
		buildHandlersStructure();
	}

	public StatusHandlerDescriptor getDefaultHandlerDescriptor() {
		return defaultHandlerDescriptor;
	}

	public List getHandlerDescriptors(String pluginId) {
		return statusHandlerDescriptorsMap.getHandlerDescriptors(pluginId);
	}

	public StatusHandlerDescriptor getHandlerDescriptor(String statusHandlerId) {
		StatusHandlerDescriptor descriptor = null;
		for (Iterator it = statusHandlerDescriptors.iterator(); it.hasNext();) {
			descriptor = (StatusHandlerDescriptor) it.next();
			if (descriptor.getId().equals(statusHandlerId)) {
				return descriptor;
			}
		}

		if (defaultHandlerDescriptor != null
				&& defaultHandlerDescriptor.getId().equals(statusHandlerId)) {
			return defaultHandlerDescriptor;
		}

		return null;
	}

	public void dispose() {
		PlatformUI.getWorkbench().getExtensionTracker().unregisterHandler(this);
	}

	private String resolveUserStatusHandlerId(){
		String[] parameters = Platform.getCommandLineArgs();
		
		for(int i = 0; i < parameters.length - 1; i++){
			if(STATUSHANDLER_ARG.equals(parameters[i])){
				return parameters[i + 1];
			}
		}
		return null;
	}
	
	private void buildHandlersStructure() {
		statusHandlerDescriptorsMap.clear();
		defaultHandlerDescriptor = null;

		String productId = Platform.getProduct() != null ? Platform
				.getProduct().getId() : null;

		List allHandlers = new ArrayList();

		String defaultHandlerId = resolveUserStatusHandlerId();

		if (defaultHandlerId == null) {
			for (Iterator it = productBindingDescriptors.iterator(); it
					.hasNext();) {
				StatusHandlerProductBindingDescriptor descriptor = ((StatusHandlerProductBindingDescriptor) it
						.next());

				if (descriptor.getProductId().equals(productId)) {
					defaultHandlerId = descriptor.getHandlerId();
				}
			}
		}

		for (Iterator it = statusHandlerDescriptors.iterator(); it.hasNext();) {
			StatusHandlerDescriptor descriptor = ((StatusHandlerDescriptor) it
					.next());

			allHandlers.add(descriptor);
		}

		StatusHandlerDescriptor handlerDescriptor = null;

		for (Iterator it = allHandlers.iterator(); it.hasNext();) {
			handlerDescriptor = (StatusHandlerDescriptor) it.next();

			if (handlerDescriptor.getId().equals(defaultHandlerId)) {
				defaultHandlerDescriptor = handlerDescriptor;
			} else {
				statusHandlerDescriptorsMap
						.addHandlerDescriptor(handlerDescriptor);
			}
		}
	}
}

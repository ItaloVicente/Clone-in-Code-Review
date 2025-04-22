
package org.eclipse.ui.internal.splash;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IProduct;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.SafeRunner;
import org.eclipse.jface.util.SafeRunnable;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.WorkbenchPlugin;
import org.eclipse.ui.internal.registry.IWorkbenchRegistryConstants;
import org.eclipse.ui.splash.AbstractSplashHandler;

public final class SplashHandlerFactory {

	public static AbstractSplashHandler findSplashHandlerFor(IProduct product) {
		if (product == null)
			return null;

		IExtensionPoint point = Platform.getExtensionRegistry()
				.getExtensionPoint(PlatformUI.PLUGIN_ID,
						IWorkbenchRegistryConstants.PL_SPLASH_HANDLERS);

		if (point == null)
			return null;

		IExtension[] extensions = point.getExtensions();
		Map idToSplash = new HashMap(); // String->ConfigurationElement
		String[] targetId = new String[1];
		for (int i = 0; i < extensions.length; i++) {
			IConfigurationElement[] children = extensions[i]
					.getConfigurationElements();
			for (int j = 0; j < children.length; j++) {
				AbstractSplashHandler handler = processElement(children[j],
						idToSplash, targetId, product);
				if (handler != null)
					return handler;

			}
		}
		return null;
	}

	private static AbstractSplashHandler processElement(
			IConfigurationElement configurationElement, Map idToSplash,
			String[] targetId, IProduct product) {
		String type = configurationElement.getName();
		if (IWorkbenchRegistryConstants.TAG_SPLASH_HANDLER.equals(type)) {
			String id = configurationElement
					.getAttribute(IWorkbenchRegistryConstants.ATT_ID);
			if (id == null)
				return null;

			if (targetId[0] != null && id.equals(targetId[0])) {
				return create(configurationElement);
			}
			idToSplash.put(id, configurationElement);

		} else if (IWorkbenchRegistryConstants.TAG_SPLASH_HANDLER_PRODUCT_BINDING
				.equals(type)) {
			String productId = configurationElement
					.getAttribute(IWorkbenchRegistryConstants.ATT_PRODUCTID);
			if (product.getId().equals(productId) && targetId[0] == null) { // we
				targetId[0] = configurationElement
						.getAttribute(IWorkbenchRegistryConstants.ATT_SPLASH_ID);
				IConfigurationElement splashElement = (IConfigurationElement) idToSplash
						.get(targetId[0]);
				if (splashElement != null)
					return create(splashElement);
			}
		}

		return null;
	}

	private static AbstractSplashHandler create(
			final IConfigurationElement splashElement) {
		final AbstractSplashHandler[] handler = new AbstractSplashHandler[1];
		SafeRunner.run(new SafeRunnable() {

			@Override
			public void run() throws Exception {
				handler[0] = (AbstractSplashHandler) WorkbenchPlugin
						.createExtension(splashElement,
								IWorkbenchRegistryConstants.ATT_CLASS);
			}

			@Override
			public void handleException(Throwable e) {
				WorkbenchPlugin
						.log("Problem creating splash implementation", e); //$NON-NLS-1$
			}
		});

		return handler[0];
	}
}

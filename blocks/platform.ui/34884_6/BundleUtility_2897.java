
package org.eclipse.ui.internal.tweaklets;

import java.util.HashMap;
import java.util.Map;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.ui.internal.misc.StatusUtil;
import org.eclipse.ui.statushandlers.StatusManager;

public class Tweaklets {
	
	public static class TweakKey {
		Class tweakClass;

		public TweakKey(Class tweakClass) {
			this.tweakClass = tweakClass;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result
					+ ((tweakClass == null) ? 0 : tweakClass.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			final TweakKey other = (TweakKey) obj;
			if (tweakClass == null) {
				if (other.tweakClass != null)
					return false;
			} else if (!tweakClass.equals(other.tweakClass))
				return false;
			return true;
		}
	}

	private static Map defaults = new HashMap();
	private static Map tweaklets = new HashMap();

	public static void setDefault(TweakKey definition, Object implementation) {
		defaults.put(definition, implementation);
	}
	
	public static Object get(TweakKey definition) {
		Object result = tweaklets.get(definition);
		if (result == null) {
			result = createTweaklet(definition);
			if (result == null) {
				result = getDefault(definition);
			}
			Assert.isNotNull(result);
			tweaklets.put(definition, result);
		}
		return result;
	}

	private static Object getDefault(TweakKey definition) {
		return defaults.get(definition);
	}

	private static Object createTweaklet(TweakKey definition) {
		IConfigurationElement[] elements = Platform
				.getExtensionRegistry()
				.getConfigurationElementsFor("org.eclipse.ui.internalTweaklets"); //$NON-NLS-1$
		for (int i = 0; i < elements.length; i++) {
			if (definition.tweakClass.getName().equals(
					elements[i].getAttribute("definition"))) { //$NON-NLS-1$
				try {
					Object tweaklet = elements[i].createExecutableExtension("implementation"); //$NON-NLS-1$
					tweaklets.put(definition, tweaklet);
					return tweaklet;
				} catch (CoreException e) {
					StatusManager.getManager().handle(
							StatusUtil.newStatus(IStatus.ERROR,
									"Error with extension " + elements[i], e), //$NON-NLS-1$
							StatusManager.LOG);
				}
			}
		}
		return null;
	}

}

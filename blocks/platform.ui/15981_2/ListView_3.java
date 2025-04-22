package org.eclipse.e4.demo.contacts.util;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;

public class Util {
	public static String getCSSUri(String themeId, IExtensionRegistry registry) {
		IExtensionPoint extPoint = registry
				.getExtensionPoint("org.eclipse.e4.ui.css.swt.theme");

		for (IExtension e : extPoint.getExtensions()) {
			for (IConfigurationElement ce : e.getConfigurationElements()) {
				if (ce.getName().equals("theme")
						&& ce.getAttribute("id").equals(themeId)) {
					return "platform:/plugin/" + ce.getContributor().getName()
							+ "/" + ce.getAttribute("basestylesheeturi");
				}
			}
		}
		return null;
	}
}

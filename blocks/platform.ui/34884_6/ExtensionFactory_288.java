
package org.eclipse.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IAdapterManager;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExecutableExtension;
import org.eclipse.core.runtime.Platform;
import org.eclipse.ui.internal.WorkbenchPlugin;
import org.osgi.framework.Bundle;
import org.osgi.service.packageadmin.ExportedPackage;
import org.osgi.service.packageadmin.PackageAdmin;
import org.osgi.util.tracker.ServiceTracker;

public final class BasicWorkingSetElementAdapter implements
		IWorkingSetElementAdapter, IExecutableExtension {

	private class Type {
		private static final int NONE = 0;
		private static final int ADAPT = 1;
		String className;
		int flags;
	}

	private Type[] preferredTypes = new Type[0];

	private ServiceTracker packageTracker;

	@Override
	public IAdaptable[] adaptElements(IWorkingSet ws, IAdaptable[] elements) {
		List adaptedElements = new ArrayList();
		for (int i = 0; i < elements.length; i++) {
			IAdaptable adaptable = adapt(elements[i]);
			if (adaptable != null)
				adaptedElements.add(adaptable);
		}

		return (IAdaptable[]) adaptedElements
				.toArray(new IAdaptable[adaptedElements.size()]);
	}

	private IAdaptable adapt(IAdaptable adaptable) {
		for (int i = 0; i < preferredTypes.length; i++) {
			IAdaptable adaptedAdaptable = adapt(preferredTypes[i], adaptable);
			if (adaptedAdaptable != null)
				return adaptedAdaptable;
		}
		return null;
	}

	private IAdaptable adapt(Type type, IAdaptable adaptable) {
		IAdapterManager adapterManager = Platform.getAdapterManager();
		Class[] directClasses = adapterManager.computeClassOrder(adaptable
				.getClass());
		for (int i = 0; i < directClasses.length; i++) {
			Class clazz = directClasses[i];
			if (clazz.getName().equals(type.className))
				return adaptable;
		}

		if ((type.flags & Type.ADAPT) != 0) {
			Object adapted = adapterManager.getAdapter(adaptable,
					type.className);
			if (adapted instanceof IAdaptable)
				return (IAdaptable) adapted;

			PackageAdmin admin = getPackageAdmin();
			if (admin != null) {
				int lastDot = type.className.lastIndexOf('.');
				if (lastDot > 0) { // this lives in a package
					String packageName = type.className.substring(0, lastDot);
					ExportedPackage[] packages = admin
							.getExportedPackages(packageName);
					if (packages != null && packages.length == 1) {
						if (packages[0].getExportingBundle().getState() == Bundle.ACTIVE) {
							try {
								adapted = adaptable.getAdapter(packages[0]
										.getExportingBundle().loadClass(
												type.className));
								if (adapted instanceof IAdaptable)
									return (IAdaptable) adapted;

							} catch (ClassNotFoundException e) {
								WorkbenchPlugin.log(e);
							}
						}
					}
				}
			}
		}
		return null;
	}

	@Override
	public void dispose() {
		if (packageTracker != null)
			packageTracker.close();
	}

	@Override
	public void setInitializationData(IConfigurationElement config,
			String propertyName, Object data) {

		if (data instanceof String) {
			List preferredTypes = new ArrayList(0);
			for (StringTokenizer toker = new StringTokenizer((String) data, ","); toker.hasMoreTokens();) {//$NON-NLS-1$
				String classNameAndOptions = toker.nextToken();
				Type record = new Type();
				parseOptions(classNameAndOptions, record);
				preferredTypes.add(record);
			}
			this.preferredTypes = (Type[]) preferredTypes
					.toArray(new Type[preferredTypes.size()]);
		}
	}

	private void parseOptions(String classNameAndOptions, Type record) {
		for (StringTokenizer toker = new StringTokenizer(classNameAndOptions,
				";"); toker.hasMoreTokens();) { //$NON-NLS-1$
			String token = toker.nextToken();
			if (record.className == null)
				record.className = token;
			else {
				for (StringTokenizer pair = new StringTokenizer(token, "="); pair.hasMoreTokens();) {//$NON-NLS-1$
					if (pair.countTokens() == 2) {
						String param = pair.nextToken();
						String value = pair.nextToken();
						if ("adapt".equals(param)) { //$NON-NLS-1$
							record.flags ^= "true".equals(value) ? Type.ADAPT : Type.NONE; //$NON-NLS-1$
						}
					}
				}
			}
		}
	}

	private PackageAdmin getPackageAdmin() {
		if (packageTracker == null) {
			packageTracker = new ServiceTracker(WorkbenchPlugin.getDefault()
					.getBundleContext(), PackageAdmin.class.getName(), null);
			packageTracker.open();
		}

		return (PackageAdmin) packageTracker.getService();
	}
}

package org.eclipse.e4.ui.internal.css.swt;

import org.eclipse.e4.ui.internal.css.swt.definition.IColorAndFontProvider;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;

public class ColorAndFontUtil {

	static IColorAndFontProvider colorAndFontProvider = null;

	static {
		if (FrameworkUtil.getBundle(ColorAndFontUtil.class).getBundleContext() != null) {
			ServiceTracker<IColorAndFontProvider, IColorAndFontProvider> colorAndFontProviderTracker = new ServiceTracker<>(
					FrameworkUtil.getBundle(ColorAndFontUtil.class).getBundleContext(),
					IColorAndFontProvider.class.getName(), null) {
				@Override
				public IColorAndFontProvider addingService(ServiceReference<IColorAndFontProvider> reference) {
					colorAndFontProvider = super.addingService(reference);
					return colorAndFontProvider;
				}
			};
			colorAndFontProviderTracker.open();
		}
	}

	public static IColorAndFontProvider getColorAndFontProvider() {
		return colorAndFontProvider;
	}

}

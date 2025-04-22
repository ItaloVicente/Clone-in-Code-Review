
package org.eclipse.jgit.nls;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class NLS {

	private static final InheritableThreadLocal<NLS> local = new InheritableThreadLocal<NLS>();

	public static void setLocale(Locale locale) {
		local.set(new NLS(locale));
	}

	public static void useJVMDefaultLocale() {
		local.set(null);
	}

	public static <T extends TranslationBundle> T getBundleFor(Class<T> type) {
		NLS self = local.get();
		if (self == null) {
			self = new NLS(Locale.getDefault());
		}
		return self.get(type);
	}

	private NLS(Locale locale) {
		this.locale = locale;
	}

	private Locale locale;
	private Map<Class

	private synchronized <T extends TranslationBundle> T get(Class<T> type) {
		try {
			TranslationBundle bundle = map.get(type);
			if (bundle == null) {
				bundle = type.newInstance();
				bundle.load(locale);
				map.put(type
			}
			return (T) bundle;
		} catch (InstantiationException e) {
			throw new Error(e);
		} catch (IllegalAccessException e) {
			throw new Error(e);
		}
	}
}

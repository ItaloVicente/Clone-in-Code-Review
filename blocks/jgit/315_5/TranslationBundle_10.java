
package org.eclipse.jgit.nls;

import java.util.Locale;
import java.util.concurrent.ConcurrentHashMap;

import org.eclipse.jgit.errors.TranslationBundleLoadingException;
import org.eclipse.jgit.errors.TranslationStringMissingException;

public class NLS {

	private static final InheritableThreadLocal<NLS> local = new InheritableThreadLocal<NLS>() {
		protected NLS initialValue() {
			return new NLS(Locale.getDefault());
		}
	};

	public static void setLocale(Locale locale) {
		local.set(new NLS(locale));
	}

	public static void useJVMDefaultLocale() {
		local.set(new NLS(Locale.getDefault()));
	}

	public static <T extends TranslationBundle> T getBundleFor(Class<T> type) {
		return local.get().get(type);
	}

	final private Locale locale;
	final private ConcurrentHashMap<Class

	private NLS(Locale locale) {
		this.locale = locale;
	}

	private <T extends TranslationBundle> T get(Class<T> type) {
		TranslationBundle bundle = map.get(type);
		if (bundle == null) {
			bundle = GlobalBundleCache.lookupBundle(locale
			map.putIfAbsent(type
		}
		return (T) bundle;
	}
}

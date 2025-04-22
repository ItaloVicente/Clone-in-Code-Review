
package org.eclipse.jgit.nls;

import java.util.Locale;
import java.util.concurrent.ConcurrentHashMap;

import org.eclipse.jgit.errors.TranslationBundleLoadingException;
import org.eclipse.jgit.errors.TranslationStringMissingException;

public class NLS {
	public static final Locale ROOT_LOCALE = new Locale(""

	private static final InheritableThreadLocal<NLS> local = new InheritableThreadLocal<>();

	public static void setLocale(Locale locale) {
		local.set(new NLS(locale));
	}

	public static void useJVMDefaultLocale() {
		useJVMDefaultInternal();
	}

	private static NLS useJVMDefaultInternal() {
		NLS b = new NLS(Locale.getDefault());
		local.set(b);
		return b;
	}

	public static <T extends TranslationBundle> T getBundleFor(Class<T> type) {
		NLS b = local.get();
		if (b == null) {
			b = useJVMDefaultInternal();
		}
		return b.get(type);
	}

	final private Locale locale;
	final private ConcurrentHashMap<Class

	private NLS(Locale locale) {
		this.locale = locale;
	}

	@SuppressWarnings("unchecked")
	private <T extends TranslationBundle> T get(Class<T> type) {
		TranslationBundle bundle = map.get(type);
		if (bundle == null) {
			bundle = GlobalBundleCache.lookupBundle(locale
			TranslationBundle old = map.putIfAbsent(type
			if (old != null)
				bundle = old;
		}
		return (T) bundle;
	}
}

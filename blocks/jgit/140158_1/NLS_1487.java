
package org.eclipse.jgit.nls;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.eclipse.jgit.errors.TranslationBundleLoadingException;
import org.eclipse.jgit.errors.TranslationStringMissingException;

class GlobalBundleCache {
	private static final Map<Locale
		= new HashMap<>();

	@SuppressWarnings("unchecked")
	static synchronized <T extends TranslationBundle> T lookupBundle(Locale locale
		try {
			Map<Class
			if (bundles == null) {
				bundles = new HashMap<>();
				cachedBundles.put(locale
			}
			TranslationBundle bundle = bundles.get(type);
			if (bundle == null) {
				bundle = type.newInstance();
				bundle.load(locale);
				bundles.put(type
			}
			return (T) bundle;
		} catch (InstantiationException | IllegalAccessException e) {
			throw new Error(e);
		}
	}
}

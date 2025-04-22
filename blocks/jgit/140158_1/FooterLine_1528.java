
package org.eclipse.jgit.revwalk;

import java.util.Locale;

import org.eclipse.jgit.lib.Constants;

public final class FooterKey {



	private final String name;

	final byte[] raw;

	public FooterKey(String keyName) {
		name = keyName;
		raw = Constants.encode(keyName.toLowerCase(Locale.ROOT));
	}

	public String getName() {
		return name;
	}

	@SuppressWarnings("nls")
	@Override
	public String toString() {
		return "FooterKey[" + name + "]";
	}
}

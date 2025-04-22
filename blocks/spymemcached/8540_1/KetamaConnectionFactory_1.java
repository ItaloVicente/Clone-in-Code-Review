package net.spy.memcached;

import java.util.HashMap;
import java.util.Map;

public final class HashAlgorithmRegistry {

	private static final Map<String, HashAlgorithm> REGISTRY = new HashMap<
			String, HashAlgorithm>();
	static {
		for (DefaultHashAlgorithm alg : DefaultHashAlgorithm.values()) {
			registerHashAlgorithm(
					alg.name().replace("[_HASH]$", "").toLowerCase(), alg);
		}
	}

	public static synchronized void registerHashAlgorithm(
			String name, HashAlgorithm alg) {
		if (name != null) {
			REGISTRY.put(name.toLowerCase(), alg);
		}
	}

	public static synchronized HashAlgorithm lookupHashAlgorithm(String name) {
		return name == null ? null : REGISTRY.get(name.toLowerCase());
	}
}

package org.eclipse.jgit.transport.sshd;

import java.nio.file.Path;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;

import javax.security.auth.DestroyFailedException;

public class JGitKeyCache implements KeyCache {

	private AtomicReference<Map<Path
			new ConcurrentHashMap<>());

	@Override
	public KeyPair get(Path path
			Function<? super Path
		return cache.get().computeIfAbsent(path
	}

	@Override
	public void close() {
		Map<Path
		if (map == null) {
			return;
		}
		for (KeyPair k : map.values()) {
			PrivateKey p = k.getPrivate();
			try {
				p.destroy();
			} catch (DestroyFailedException e) {
			}
		}
		map.clear();
	}
}

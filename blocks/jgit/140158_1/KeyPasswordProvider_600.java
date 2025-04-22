package org.eclipse.jgit.transport.sshd;

import java.nio.file.Path;
import java.security.KeyPair;
import java.util.function.Function;

public interface KeyCache {

	KeyPair get(Path path

	void close();
}

package org.eclipse.jgit.lib;

import java.util.Map.Entry;
import java.util.concurrent.locks.ReadWriteLock;

public interface RefCache {

	ReadWriteLock getLock();

	void reload(Iterable<Entry<String

	void insert(Ref ref);

	void delete(String refName);

	void rename(Ref oldRef

}

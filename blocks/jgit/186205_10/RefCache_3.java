package org.eclipse.jgit.lib;

import java.util.Map.Entry;

import org.eclipse.jgit.lib.RefUpdate.Result;

public interface RefCache {

	void onUpdated(RefUpdate updated

	void onBatchUpdated(Iterable<Entry<String

	void onDeleted(RefUpdate deleted

	void onLinked(RefUpdate linked

	void onRenamed(RefUpdate src

}

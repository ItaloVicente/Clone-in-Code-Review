
package org.eclipse.jgit.storage.file;

import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.RepositoryTestCase;
import org.junit.Test;

import java.util.*;
import java.util.concurrent.*;

public class ObjectDirectoryTest extends RepositoryTestCase {

	@Test
	public void testConcurrentInsertionOfBlobsToTheSameNewFanOutDirectory()
			throws Exception {
		ExecutorService e = Executors.newCachedThreadPool();
		for (int i=0; i < 100; ++i) {
			ObjectDirectory db = createBareRepository().getObjectDatabase();
			for (Future f : e.invokeAll(blobInsertersForTheSameFanOutDir(db))) {
				f.get();
			}
		}
	}

	private Collection<Callable<ObjectId>> blobInsertersForTheSameFanOutDir(
			final ObjectDirectory db) {
		Callable<ObjectId> callable = new Callable<ObjectId>() {
			public ObjectId call() throws Exception {
				return db.newInserter().insert(Constants.OBJ_BLOB
			}
		};
		return Collections.nCopies(4
	}

}

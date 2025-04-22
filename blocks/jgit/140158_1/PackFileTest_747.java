
package org.eclipse.jgit.internal.storage.file;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.junit.RepositoryTestCase;
import org.eclipse.jgit.lib.ConfigConstants;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.storage.file.FileBasedConfig;
import org.junit.Assume;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ObjectDirectoryTest extends RepositoryTestCase {

	@Rule
	public ExpectedException expectedEx = ExpectedException.none();

	@Test
	public void testConcurrentInsertionOfBlobsToTheSameNewFanOutDirectory()
			throws Exception {
		ExecutorService e = Executors.newCachedThreadPool();
		for (int i=0; i < 100; ++i) {
			ObjectDirectory dir = createBareRepository().getObjectDatabase();
			for (Future f : e.invokeAll(blobInsertersForTheSameFanOutDir(dir))) {
				f.get();
			}
		}
	}

	@Test
	public void testScanningForPackfiles() throws Exception {
		ObjectId unknownID = ObjectId
				.fromString("c0ffee09d0b63d694bf49bc1e6847473f42d4a8c");
		GC gc = new GC(db);
		gc.setExpireAgeMillis(0);
		gc.setPackExpireAgeMillis(0);

		try (FileRepository receivingDB = new FileRepository(
				db.getDirectory())) {
			FileBasedConfig cfg = receivingDB.getConfig();
			cfg.setBoolean(ConfigConstants.CONFIG_CORE_SECTION
					ConfigConstants.CONFIG_KEY_TRUSTFOLDERSTAT
			cfg.save();

			ObjectId id = commitFile("file.txt"
			gc.gc();
			assertFalse(receivingDB.getObjectDatabase().has(unknownID));
			assertTrue(receivingDB.getObjectDatabase().hasPackedObject(id));

			File packsFolder = receivingDB.getObjectDatabase()
					.getPackDirectory();
			File tmpFile = new File(packsFolder
			RevCommit id2 = commitFile("file.txt"
			fsTick(null);

			assertTrue(tmpFile.createNewFile());
			assertFalse(receivingDB.getObjectDatabase().has(unknownID));

			gc.gc();

			Thread.sleep(2600);

			File[] ret = packsFolder.listFiles(new FilenameFilter() {
				@Override
				public boolean accept(File dir
					return name.endsWith(".pack");
				}
			});
			assertTrue(ret != null && ret.length == 1);
			Assume.assumeTrue(tmpFile.lastModified() == ret[0].lastModified());

			assertFalse(receivingDB.getObjectDatabase().has(unknownID));
			assertTrue(receivingDB.getObjectDatabase().has(id2));
		}
	}

	@Test
	public void testShallowFile()
			throws Exception {
		FileRepository repository = createBareRepository();
		ObjectDirectory dir = repository.getObjectDatabase();

		String commit = "d3148f9410b071edd4a4c85d2a43d1fa2574b0d2";
		try (PrintWriter writer = new PrintWriter(
				new File(repository.getDirectory()
				UTF_8.name())) {
			writer.println(commit);
		}
		Set<ObjectId> shallowCommits = dir.getShallowCommits();
		assertTrue(shallowCommits.remove(ObjectId.fromString(commit)));
		assertTrue(shallowCommits.isEmpty());
	}

	@Test
	public void testShallowFileCorrupt()
			throws Exception {
		FileRepository repository = createBareRepository();
		ObjectDirectory dir = repository.getObjectDatabase();

		String commit = "X3148f9410b071edd4a4c85d2a43d1fa2574b0d2";
		try (PrintWriter writer = new PrintWriter(
				new File(repository.getDirectory()
				UTF_8.name())) {
			writer.println(commit);
		}

		expectedEx.expect(IOException.class);
		expectedEx.expectMessage(MessageFormat
				.format(JGitText.get().badShallowLine
		dir.getShallowCommits();
	}

	private Collection<Callable<ObjectId>> blobInsertersForTheSameFanOutDir(
			final ObjectDirectory dir) {
		Callable<ObjectId> callable = new Callable<ObjectId>() {
			@Override
			public ObjectId call() throws Exception {
				return dir.newInserter().insert(Constants.OBJ_BLOB
			}
		};
		return Collections.nCopies(4
	}

}

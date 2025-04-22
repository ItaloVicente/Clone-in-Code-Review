package org.eclipse.egit.core.synchronize;

import java.io.File;
import java.io.IOException;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.junit.LocalDiskRepositoryTestCase;
import org.eclipse.jgit.lib.AbbreviatedObjectId;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.storage.file.FileRepository;
import org.eclipse.jgit.util.FileUtils;
import org.junit.Before;

public abstract class AbstractCacheTest extends LocalDiskRepositoryTestCase {

	protected FileRepository db;

	protected static final String INITIAL_TAG = "initial-tag";

	protected static final AbbreviatedObjectId ZERO_ID = AbbreviatedObjectId.fromObjectId(ObjectId.zeroId());

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();
		db = createWorkRepository();
		Git git = new Git(db);
		git.commit().setMessage("initial commit").call();
		git.tag().setName(INITIAL_TAG).call();
	}

	protected File writeTrashFile(final String name, final String data)
			throws IOException {
		File path = new File(db.getWorkTree(), name);
		write(path, data);
		return path;
	}

	protected void deleteTrashFile(final String name) throws IOException {
		File path = new File(db.getWorkTree(), name);
		FileUtils.delete(path);
	}


}

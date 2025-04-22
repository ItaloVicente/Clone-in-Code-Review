package org.eclipse.jgit.lib;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jgit.junit.JGitTestUtil;
import org.eclipse.jgit.junit.LocalDiskRepositoryTestCase;
import org.eclipse.jgit.pgm.CLIGitCommand;
import org.eclipse.jgit.storage.file.FileRepository;
import org.junit.Before;

public class RepositoryTestCase extends LocalDiskRepositoryTestCase {
	protected FileRepository db;

	protected File trash;

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
		db = createWorkRepository();
		trash = db.getWorkTree();
	}

	protected String[] execute(String... cmds) throws Exception {
		List<String> result = new ArrayList<String>(cmds.length);
		for (String cmd : cmds)
			result.addAll(CLIGitCommand.execute(cmd
		return result.toArray(new String[0]);
	}

	protected File writeTrashFile(final String name
			throws IOException {
		return JGitTestUtil.writeTrashFile(db
	}
}

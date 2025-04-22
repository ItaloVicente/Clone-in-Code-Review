package org.eclipse.jgit.gitrepo;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jgit.gitrepo.BareSuperprojectWriter.BareWriterConfig;
import org.eclipse.jgit.gitrepo.RepoCommand.RemoteReader;
import org.eclipse.jgit.junit.RepositoryTestCase;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.junit.Test;

public class BareSuperprojectWriterTest extends RepositoryTestCase {

	private static final String SHA1_A = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";

	@Override
	public void setUp() throws Exception {
		super.setUp();
	}

	@Test
	public void write_setGitModulesContents() throws Exception {
		try (Repository bareRepo = createBareRepository()) {
			Map<String
			attrs.put("a-key"
			attrs.put("-b-key"

			RepoProject repoProject = new RepoProject("subprojectX"
					"refs/heads/branch-x"

			RemoteReader mockRemoteReader = mock(RemoteReader.class);
					"refs/heads/branch-x"))
							.thenReturn(ObjectId.fromString(SHA1_A));

			BareSuperprojectWriter w = new BareSuperprojectWriter(bareRepo
					null
					BareWriterConfig.getDefault()

			RevCommit commit = w.write(List.of(repoProject));

			String contents = readContents(bareRepo
			List<String> contentLines = Arrays
					.asList(contents.split("\n"));
			assertThat(contentLines.get(0)
					is("[submodule \"subprojectX\"]"));
			assertThat(contentLines.subList(1
					containsInAnyOrder(is("\tbranch = refs/heads/branch-x")
							is("\tpath = path/to")
		}
	}

	private String readContents(Repository repo
			String path) throws Exception {
		String idStr = commit.getId().name() + ":" + path;
		ObjectId modId = repo.resolve(idStr);
		try (ObjectReader reader = repo.newObjectReader()) {
			return new String(
					reader.open(modId).getCachedBytes(Integer.MAX_VALUE));

		}
	}
}

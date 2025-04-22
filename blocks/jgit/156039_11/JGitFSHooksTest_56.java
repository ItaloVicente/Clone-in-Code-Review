package org.eclipse.jgit.niofs.internal.op.commands;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import java.sql.Ref;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.jgit.niofs.internal.op.GitImpl;
import org.junit.Before;
import org.junit.Test;

public class SyncRemoteTest {

	private SyncRemote syncRemote;

	@Before
	public void setup() {
		syncRemote = new SyncRemote(mock(GitImpl.class)
	}

	@Test
	public void fillBranchesTest() {
		final List<Ref> branches = Arrays.asList(createBranch("refs/heads/local/branch1")
				createBranch("refs/heads/localBranch2")
				createBranch("refs/remotes/upstream/remoteBranch2"));

		final List<String> remoteBranches = new ArrayList<>();
		final List<String> localBranches = new ArrayList<>();

		syncRemote.fillBranches(branches

		assertEquals(2
		assertEquals("remote/branch1"
		assertEquals("remoteBranch2"

		assertEquals(2
		assertEquals("local/branch1"
		assertEquals("localBranch2"
	}

	private Ref createBranch(String branchName) {
		final Ref branch = mock(Ref.class);
		doReturn(branchName).when(branch).getName();

		return branch;
	}
}

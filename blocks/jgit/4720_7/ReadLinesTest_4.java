package org.eclipse.jgit.pgm;

import static org.junit.Assert.assertArrayEquals;

import org.eclipse.jgit.lib.RepositoryTestCase;
import org.junit.Test;

public class CommitAndLogCommandTest extends RepositoryTestCase {
	@Test
	public void testCommitAmend() throws Exception {
						"[master 101cffba0364877df1942891eba7f465f628a3d2] first comit"
						""
						"[master d2169869dadf16549be20dcf8c207349d2ed6c62] first commit"
						""
						"commit d2169869dadf16549be20dcf8c207349d2ed6c62"
						"Author: GIT_COMMITTER_NAME <GIT_COMMITTER_EMAIL>"
						"Date:   Sat Aug 15 20:12:58 2009 -0330"
						""
						"    first commit"
						""
				}
						"git commit --amend -m 'first commit'"
						"git log"));
	}
}

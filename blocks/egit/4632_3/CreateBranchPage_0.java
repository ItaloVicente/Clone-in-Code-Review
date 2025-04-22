package org.eclipse.egit.ui.internal.repository;

import static org.eclipse.egit.ui.internal.repository.CreateBranchPage.getProposedTargetName;
import static org.eclipse.jgit.lib.Constants.R_REMOTES;
import static org.eclipse.jgit.lib.Constants.R_TAGS;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

public class CreateBranchPageTest {

	@Test
	public void computeTargetName() {
		assertNull(getProposedTargetName(null));
		assertNull(getProposedTargetName(""));
		assertEquals("a/b", getProposedTargetName(R_REMOTES + "origin/a/b"));
		assertEquals("r1", getProposedTargetName(R_REMOTES + "review/r1"));
		assertEquals("v1", getProposedTargetName(R_TAGS + "v1"));
	}
}

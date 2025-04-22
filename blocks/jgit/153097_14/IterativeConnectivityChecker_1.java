
package org.eclipse.jgit.transport.internal;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ProgressMonitor;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.FakeRevCommit;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.transport.PackParser;
import org.eclipse.jgit.transport.ReceiveCommand;
import org.eclipse.jgit.transport.internal.ConnectivityChecker.ConnectivityCheckInfo;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

public class IterativeConnectivityCheckerTest {
	private static final ObjectId BRANCH_HEAD = ObjectId
			.fromString("F00DBEEFF00DBEEFF00DBEEFF00DBEEFF00DBEEF");

	private static final ObjectId OPEN_REVIEW = ObjectId
			.fromString("BAADF00DBAADF00DBAADF00DBAADF00DBAADF00D");

	private static final ObjectId NEW_COMMIT = ObjectId
			.fromString("BADDCAFEBADDCAFEBADDCAFEBADDCAFEBADDCAFE");

	private static final ObjectId OTHER_HAVE = ObjectId
			.fromString("DEADBEEFDEADBEEFDEADBEEFDEADBEEFDEADBEEF");

	private static final Set<ObjectId> ADVERTISED_HAVES = wrap(BRANCH_HEAD
			OPEN_REVIEW

	private static final ReceiveCommand CREATE_NEW_BRANCH_COMMAND = new ReceiveCommand(
			ObjectId.zeroId()

	private static final ReceiveCommand PUSH_OLD_BRANCH_COMMAND = new ReceiveCommand(
			BRANCH_HEAD

	@Rule
	public MockitoRule rule = MockitoJUnit.rule();

	@Mock
	private ConnectivityChecker connectivityCheckerDelegate;

	@Mock
	private ProgressMonitor pm;

	@Mock
	private PackParser parser;

	@Mock
	private Repository repository;

	@Mock
	private RevWalk walk;

	private FakeRevCommit branchHeadCommitObject;

	private FakeRevCommit openReviewCommitObject;

	private FakeRevCommit newCommitObject;

	private ConnectivityCheckInfo connectivityCheckInfo;
	private IterativeConnectivityChecker connectivityChecker;

	@Before
	public void setUp() throws MissingObjectException
		connectivityChecker = new IterativeConnectivityChecker(
				connectivityCheckerDelegate);
		connectivityCheckInfo = new ConnectivityCheckInfo();
		connectivityCheckInfo.setParser(parser);
		connectivityCheckInfo.setRepository(repository);
		connectivityCheckInfo.setWalk(walk);

		branchHeadCommitObject = new FakeRevCommit(BRANCH_HEAD);
		openReviewCommitObject = new FakeRevCommit(OPEN_REVIEW);
		newCommitObject = new FakeRevCommit(NEW_COMMIT);

		when(walk.parseAny(NEW_COMMIT)).thenReturn(newCommitObject);
	}

	@Test
	public void testSuccessfulNewBranchBasedOnOld() throws Exception {
		connectivityCheckInfo.setCommands(
				Collections.singletonList(CREATE_NEW_BRANCH_COMMAND));
		newCommitObject.setParents(new RevCommit[] { branchHeadCommitObject });

		connectivityChecker.checkConnectivity(connectivityCheckInfo
				ADVERTISED_HAVES

		verify(connectivityCheckerDelegate).checkConnectivity(
				connectivityCheckInfo
				pm);
	}

	@Test
	public void testSuccessfulNewBranchBasedOnOldWithTip() throws Exception {
		connectivityCheckInfo.setCommands(
				Collections.singletonList(CREATE_NEW_BRANCH_COMMAND));
		newCommitObject.setParents(new RevCommit[] { branchHeadCommitObject });

		connectivityChecker.setForcedHaves(wrap(OPEN_REVIEW));

		connectivityChecker.checkConnectivity(connectivityCheckInfo
				ADVERTISED_HAVES

		verify(connectivityCheckerDelegate).checkConnectivity(
				connectivityCheckInfo
				pm);
	}

	@Test
	public void testSuccessfulNewBranchMerge() throws Exception {
		connectivityCheckInfo.setCommands(
				Collections.singletonList(CREATE_NEW_BRANCH_COMMAND));
		newCommitObject.setParents(new RevCommit[] { branchHeadCommitObject
				openReviewCommitObject });

		connectivityChecker.checkConnectivity(connectivityCheckInfo
				ADVERTISED_HAVES

		verify(connectivityCheckerDelegate).checkConnectivity(
				connectivityCheckInfo
	}

	@Test
	public void testSuccessfulNewBranchBasedOnNewWithTip() throws Exception {
		connectivityCheckInfo.setCommands(
				Collections.singletonList(CREATE_NEW_BRANCH_COMMAND));
		newCommitObject.setParents(new RevCommit[] {});

		connectivityChecker.setForcedHaves(wrap(OPEN_REVIEW));

		connectivityChecker.checkConnectivity(connectivityCheckInfo
				ADVERTISED_HAVES

		verify(connectivityCheckerDelegate).checkConnectivity(
				connectivityCheckInfo
	}

	@Test
	public void testSuccessfulPushOldBranch() throws Exception {
		connectivityCheckInfo.setCommands(
				Collections.singletonList(PUSH_OLD_BRANCH_COMMAND));
		newCommitObject.setParents(new RevCommit[] { branchHeadCommitObject });

		connectivityChecker.checkConnectivity(connectivityCheckInfo
				ADVERTISED_HAVES

		verify(connectivityCheckerDelegate).checkConnectivity(
				connectivityCheckInfo
				pm);
	}

	@Test
	public void testSuccessfulPushOldBranchMergeCommit() throws Exception {
		connectivityCheckInfo.setCommands(
				Collections.singletonList(PUSH_OLD_BRANCH_COMMAND));
		newCommitObject.setParents(new RevCommit[] { branchHeadCommitObject
				openReviewCommitObject });

		connectivityChecker.checkConnectivity(connectivityCheckInfo
				ADVERTISED_HAVES

		verify(connectivityCheckerDelegate).checkConnectivity(
				connectivityCheckInfo
				pm);
	}


	@Test
	public void testNoChecksIfCantFindSubset() throws Exception {
		connectivityCheckInfo.setCommands(
				Collections.singletonList(CREATE_NEW_BRANCH_COMMAND));
		newCommitObject.setParents(new RevCommit[] {});

		connectivityChecker.checkConnectivity(connectivityCheckInfo
				ADVERTISED_HAVES

		verify(connectivityCheckerDelegate).checkConnectivity(
				connectivityCheckInfo
	}

	@Test
	public void testReiterateInCaseNotSuccessful() throws Exception {
		connectivityCheckInfo.setCommands(
				Collections.singletonList(CREATE_NEW_BRANCH_COMMAND));
		newCommitObject.setParents(new RevCommit[] { branchHeadCommitObject });

		doThrow(new MissingObjectException(branchHeadCommitObject
				Constants.OBJ_COMMIT)).when(connectivityCheckerDelegate)
						.checkConnectivity(connectivityCheckInfo

		connectivityChecker.checkConnectivity(connectivityCheckInfo
				ADVERTISED_HAVES

		verify(connectivityCheckerDelegate)
				.checkConnectivity(connectivityCheckInfo
	}

	private static Set<ObjectId> wrap(ObjectId... objectIds) {
		return new HashSet<>(Arrays.asList(objectIds));
	}
}

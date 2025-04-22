
package org.eclipse.jgit.lib;

import java.io.IOException;

import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.revwalk.RevWalkUtils;
import org.eclipse.jgit.revwalk.filter.RevFilter;

public class BranchTrackingStatus {

	public static BranchTrackingStatus of(Repository repository
			throws IOException {

		String shortBranchName = Repository.shortenRefName(branchName);
		String fullBranchName = Constants.R_HEADS + shortBranchName;
		BranchConfig branchConfig = new BranchConfig(repository.getConfig()
				shortBranchName);

		String trackingBranch = branchConfig.getTrackingBranch();
		if (trackingBranch == null)
			return null;

		Ref tracking = repository.exactRef(trackingBranch);
		if (tracking == null)
			return null;

		Ref local = repository.exactRef(fullBranchName);
		if (local == null)
			return null;

		try (RevWalk walk = new RevWalk(repository)) {

			RevCommit localCommit = walk.parseCommit(local.getObjectId());
			RevCommit trackingCommit = walk.parseCommit(tracking.getObjectId());

			walk.setRevFilter(RevFilter.MERGE_BASE);
			walk.markStart(localCommit);
			walk.markStart(trackingCommit);
			RevCommit mergeBase = walk.next();

			walk.reset();
			walk.setRevFilter(RevFilter.ALL);
			int aheadCount = RevWalkUtils.count(walk
			int behindCount = RevWalkUtils.count(walk
					mergeBase);

			return new BranchTrackingStatus(trackingBranch
					behindCount);
		}
	}

	private final String remoteTrackingBranch;

	private final int aheadCount;

	private final int behindCount;

	private BranchTrackingStatus(String remoteTrackingBranch
			int behindCount) {
		this.remoteTrackingBranch = remoteTrackingBranch;
		this.aheadCount = aheadCount;
		this.behindCount = behindCount;
	}

	public String getRemoteTrackingBranch() {
		return remoteTrackingBranch;
	}

	public int getAheadCount() {
		return aheadCount;
	}

	public int getBehindCount() {
		return behindCount;
	}
}

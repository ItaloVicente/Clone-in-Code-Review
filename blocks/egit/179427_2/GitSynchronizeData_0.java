package org.eclipse.egit.core.revisions;

import java.util.Objects;

import org.eclipse.egit.core.internal.storage.GitFileRevision;
import org.eclipse.jgit.dircache.DirCacheCheckout.CheckoutMetadata;
import org.eclipse.jgit.dircache.DirCacheEntry;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.team.core.history.IFileRevision;

public final class FileRevisionFactory {

	private FileRevisionFactory() {
	}

	public static IFileRevision inCommit(Repository repository,
			RevCommit commit, String gitPath) {
		return GitFileRevision.inCommit(repository, commit, gitPath, null,
				null);
	}

	public static IFileRevision inCommit(Repository repository,
			RevCommit commit, String gitPath, ObjectId blobId,
			CheckoutMetadata metadata) {
		return GitFileRevision.inCommit(repository, commit, gitPath,
				Objects.requireNonNull(blobId),
				Objects.requireNonNull(metadata));
	}

	public static IFileRevision inIndex(Repository repository,
			String gitPath) {
		return GitFileRevision.inIndex(repository, gitPath);
	}

	public static IFileRevision inIndex(Repository repository,
			String gitPath, int stage) {
		return GitFileRevision.inIndex(repository, gitPath, stage);
	}

}

package org.eclipse.egit.core.internal.merge;

import org.eclipse.egit.core.synchronize.GitRemoteResource;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.team.internal.core.mapping.ResourceVariantFileRevision;

class GitResourceVariantFileRevision extends ResourceVariantFileRevision {

	public GitResourceVariantFileRevision(GitRemoteResource variant) {
		super(variant);
	}

	@Override
	public GitRemoteResource getVariant() {
		return (GitRemoteResource) super.getVariant();
	}

	@Override
	public String getContentIdentifier() {
		return getVariant().getCommitId().getId().getName();
	}

	@Override
	public long getTimestamp() {
		final PersonIdent author = getVariant().getCommitId().getAuthorIdent();
		if (author != null) {
			return author.getWhen().getTime();
		}
		return super.getTimestamp();
	}

	@Override
	public String getAuthor() {
		final PersonIdent author = getVariant().getCommitId().getAuthorIdent();
		if (author != null) {
			return author.getName();
		}
		return super.getAuthor();
	}

	@Override
	public String getComment() {
		return getVariant().getCommitId().getFullMessage();

	}
}

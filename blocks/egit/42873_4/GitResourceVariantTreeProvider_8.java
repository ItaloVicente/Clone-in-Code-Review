package org.eclipse.egit.core.internal.merge;

import org.eclipse.egit.core.synchronize.GitRemoteResource;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.team.core.variants.IResourceVariant;
import org.eclipse.team.internal.core.mapping.ResourceVariantFileRevision;

class GitResourceVariantFileRevision extends ResourceVariantFileRevision {
	private final IResourceVariant variant;

	public GitResourceVariantFileRevision(IResourceVariant variant) {
		super(variant);
		this.variant = variant;
	}

	@Override
	public String getContentIdentifier() {
		if (variant instanceof GitRemoteResource)
			return ((GitRemoteResource) variant).getCommitId().getId()
					.getName();

		return super.getContentIdentifier();
	}

	@Override
	public long getTimestamp() {
		if (variant instanceof GitRemoteResource) {
			final PersonIdent author = ((GitRemoteResource) variant)
					.getCommitId().getAuthorIdent();
			if (author != null)
				return author.getWhen().getTime();
		}

		return super.getTimestamp();
	}

	@Override
	public String getAuthor() {
		if (variant instanceof GitRemoteResource) {
			final PersonIdent author = ((GitRemoteResource) variant)
					.getCommitId().getAuthorIdent();
			if (author != null)
				return author.getName();
		}

		return super.getAuthor();
	}

	@Override
	public String getComment() {
		if (variant instanceof GitRemoteResource)
			return ((GitRemoteResource) variant).getCommitId().getFullMessage();

		return super.getComment();
	}
}

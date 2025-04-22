	private CommitBuilder copy(RevCommit toCopy, ObjectId[] parents,
			PersonIdent committer, String message) {
		CommitBuilder builder = new CommitBuilder();
		builder.setParentIds(parents);
		builder.setAuthor(toCopy.getAuthorIdent());
		builder.setCommitter(committer);
		builder.setEncoding(toCopy.getEncoding());
		builder.setTreeId(toCopy.getTree());
		builder.setMessage(message);
		return builder;
	}

	private GpgSigner sign(CommitBuilder builder, GpgSigner signer,
			boolean signAll, String keyId, PersonIdent committer,
			PersonIdent oldCommitter, RevCommit original)
			throws JGitInternalException {
		if (committer.getName().equals(oldCommitter.getName()) && committer
				.getEmailAddress().equals(oldCommitter.getEmailAddress())) {
			try {
				signer.sign(builder, keyId, committer,
						CredentialsProvider.getDefault());
			} catch (CanceledException e) {
				return null;
			} catch (JGitInternalException e) {
				if (signAll) {
					throw e;
				}
				Activator.logWarning(MessageFormat.format(
						CoreText.RewordCommitOperation_cannotSign,
						Utils.getShortObjectId(commit),
						Utils.getShortObjectId(headId),
						Utils.getShortObjectId(original)), e);
				return null;
			}
		}
		return signer;

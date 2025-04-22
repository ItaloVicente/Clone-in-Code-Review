				|| RawParseUtils.parsePersonIdent(committerValue) == null) {
			statusMessage = UIText.CommitMessageComponent_MessageInvalidCommitter;
			statusType = ERROR;
			return this;
		}

		if (amending && amendingCommitInRemoteBranch) {
			statusMessage = UIText.CommitMessageComponent_AmendingCommitInRemoteBranch;
			statusType = WARNING;
			return this;
		}

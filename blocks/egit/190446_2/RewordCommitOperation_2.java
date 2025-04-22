	private String insertChangeId(RevCommit thisCommit, String message,
			PersonIdent committer) {
		ObjectId firstParentId = null;
		if (thisCommit.getParentCount() > 0) {
			firstParentId = thisCommit.getParent(0);
		}
		ObjectId changeId = ChangeIdUtil.computeChangeId(thisCommit.getTree(),
				firstParentId, thisCommit.getAuthorIdent(), committer, message);
		String msg = ChangeIdUtil.insertId(message, changeId);
		if (changeId != null) {
			msg = msg.replaceAll("\nChange-Id: I" //$NON-NLS-1$
					+ ObjectId.zeroId().getName() + '\n',
					"\nChange-Id: I" + changeId.getName() + '\n'); //$NON-NLS-1$
		}
		return msg;
	}


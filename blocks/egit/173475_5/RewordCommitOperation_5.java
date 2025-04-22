			inserter.flush();
		}
		ObjectId newHeadId = rewritten.get(headId);
		RefUpdate ru = repository.updateRef(Constants.HEAD);
		ru.setExpectedOldObjectId(headId);
		ru.setNewObjectId(newHeadId);
		ru.setForceUpdate(true);
		ru.setRefLogMessage("rebase finished: returning to " + headName //$NON-NLS-1$
				+ " after having reworded commit " + newCommitId, false); //$NON-NLS-1$
		switch (ru.update(walk)) {
		case NEW:
		case NO_CHANGE:
		case FORCED:
		case FAST_FORWARD:
			break;
		default:
			throw new TeamException(MessageFormat.format(
					CoreText.RewordCommitOperation_cannotUpdateHead,
					Utils.getShortObjectId(commit)));
		}
		ObjectId origHead = ru.getOldObjectId();
		if (origHead != null) {
			repository.writeOrigHead(origHead);
		}
		progress.worked(1);
	}

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

			final Ref result;
			if (commitId != null) {
				final RefUpdate ru = repo.updateRef(Constants.HEAD);
				ru.setNewObjectId(commitId);

				String refName = Repository.shortenRefName(getRefOrHEAD());
				ru.setRefLogMessage(message
				if (ru.forceUpdate() == RefUpdate.Result.LOCK_FAILURE)
					throw new JGitInternalException(MessageFormat.format(
							JGitText.get().cannotLock

				ObjectId origHead = ru.getOldObjectId();
				if (origHead != null)
					repo.writeOrigHead(origHead);
				result = ru.getRef();
			} else {
				result = repo.getRef(Constants.HEAD);
			}

			final RefUpdate ru = repo.updateRef(Constants.HEAD);
			ru.setNewObjectId(commitId);

			String refName = Repository.shortenRefName(ref);
			ru.setRefLogMessage(message, false);
			if (ru.forceUpdate() == RefUpdate.Result.LOCK_FAILURE)
				throw new JGitInternalException(MessageFormat.format(
						JGitText.get().cannotLock, ru.getName()));

			ObjectId origHead = ru.getOldObjectId();
			if (origHead != null)
				repo.writeOrigHead(origHead);

			ObjectId newHeadId = rewritten.get(headId);
			RefUpdate ru = repository.updateRef(Constants.HEAD);
			ru.setExpectedOldObjectId(headId);
			ru.setNewObjectId(newHeadId);
			ru.disableRefLog();
			switch (ru.forceUpdate()) {
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
		} finally {
			index.unlock();
		}
	}

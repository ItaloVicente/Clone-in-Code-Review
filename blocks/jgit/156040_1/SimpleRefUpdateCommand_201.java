	private void forceUpdate(final RefUpdate ru
			throws java.io.IOException
		final RefUpdate.Result rc = ru.forceUpdate();
		switch (rc) {
		case NEW:
		case FORCED:
		case FAST_FORWARD:
		case NO_CHANGE:
			break;
		case REJECTED:
		case LOCK_FAILURE:
			throw new ConcurrentRefUpdateException(JGitText.get().couldNotLockHEAD
		default:
			throw new JGitInternalException(
					MessageFormat.format(JGitText.get().updatingRefFailed
		}
	}

	private Result updateStore(final LockFile lock, final Result status)
			throws IOException {
		if (status == Result.NO_CHANGE)
			return status;
		lock.setNeedStatInformation(true);
		lock.write(newValue);
		String msg = getRefLogMessage();
		if (msg != null) {
			if (refLogIncludeResult) {
				String strResult = toResultString(status);
				if (strResult != null) {
					if (msg.length() > 0)
						msg = msg + ": " + strResult;
					else
						msg = strResult;
				}
			}
			RefLogWriter.append(this, msg);
		}
		if (!lock.commit())
			return Result.LOCK_FAILURE;
		db.stored(this.ref.getOrigName(),  ref.getName(), newValue, lock.getCommitLastModified());
		return status;
	}

	private static String toResultString(final Result status) {
		switch (status) {
		case FORCED:
			return "forced-update";
		case FAST_FORWARD:
			return "fast forward";
		case NEW:
			return "created";
		default:
			return null;
		}
	}


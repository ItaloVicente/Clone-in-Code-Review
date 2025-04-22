
	@Override
	protected Result doLink(final String target) throws IOException {
		lock.setNeedStatInformation(true);
		lock.write(encode(RefDirectory.SYMREF + target + '\n'));

		String msg = getRefLogMessage();
		if (msg != null)
			database.log(this
		if (!lock.commit())
			return Result.LOCK_FAILURE;
		database.storedSymbolicRef(this

		if (getRef().getStorage() == Ref.Storage.NEW)
			return Result.NEW;
		return Result.FORCED;
	}

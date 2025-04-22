	@Override
	public void link(String name, String target) throws IOException {
		LockFile lck = new LockFile(fileFor(name));
		if (!lck.lock())
			throw new IOException("Cannot lock " + name);
		lck.setNeedStatInformation(true);
		try {
			lck.write(encode(SYMREF + target + '\n'));
			if (!lck.commit())
				throw new IOException("Cannot write " + name);
		} finally {
			lck.unlock();
		}
		putLooseRef(newSymbolicRef(lck.getCommitLastModified(), name, target));




	private void handleShallowUnshallowLines()
			throws IOException {
		System.out.println(
				"handleShallowUnshallowLines: begin!");
		final File parentDirectory = this.local.getDirectory();
		if (parentDirectory == null) {
			throw new IOException("Repository.getDirectory() is null (Could not obtain $GITDIR)!");
		}
		System.out.println("handleShallowUnshallowLines-shallowFile='"
				+ parentDirectory.getAbsolutePath() + "'");
		final File shallowFile = new File(parentDirectory
		System.out.println("handleShallowUnshallowLines-shallowFile='"
				+ shallowFile.getAbsolutePath() + "'");
		LockFile shallowLockFile = new LockFile(shallowFile);
		if (!shallowLockFile.lock()) {
			throw new IOException("Could not obtain lock for $GITDIR/");
		}
		final List<ObjectId> shallowIds = new ArrayList<>();

		String line = null;
		int length = -1;
		do {
			line = pckIn.readString();
			System.out
					.println("handleShallowUnshallowLines.line='" + line + "'");
			length = line.length();
			System.out.println(
					"handleShallowUnshallowLines.length='" + length + "'");
			if (length > 0) {
				if (line.startsWith(SHALLOW)) {
					final String id = line.substring(SHALLOW.length()
							SHALLOW.length() + 40);
					final ObjectId objId = ObjectId.fromString(id);
					shallowIds.add(objId);
					System.out.println(
							"handleShallowUnshallowLines.id='" + id + "'");

				} else if (line.startsWith(UNSHALLOW)) {
					final String id = line.substring(UNSHALLOW.length()
							UNSHALLOW.length() + 40);
					final ObjectId objId = ObjectId.fromString(id);
					shallowIds.remove(objId);
					System.out.println(
							"handleShallowUnshallowLines.id='" + id + "'");
				} else {
					throw new PackProtocolException(MessageFormat.format(
							JGitText.get().expectedShallowUnshallowGot
				}
			}

		} while (length != 0);

		for (ObjectId id : shallowIds) {
			shallowLockFile.write(id);
		}
		if (shallowIds.size() > 0) {
			shallowLockFile.commit();
		} else {
			shallowLockFile.unlock();
		}
		System.out.println("handleShallowUnshallowLines: end!");
	}



	void convertToPackedRefs(boolean backup) throws IOException {
		List<Ref> all = refs.getRefs();
		File packedRefs = new File(getDirectory()
		if (packedRefs.exists()) {
			throw new IOException(MessageFormat.format(JGitText.get().fileAlreadyExists
				packedRefs.getName()));
		}

		File refsFile = new File(getDirectory()

		refs.close();

		if (backup) {
			File refsOld = new File(getDirectory()
			if (refsOld.exists()) {
				throw new IOException(MessageFormat.format(
					JGitText.get().fileAlreadyExists
			}
			FileUtils.rename(refsFile
		} else {
			refsFile.delete();
		}

		refs = new RefDirectory(this);
		refs.create();

		List<Ref> symrefs = new ArrayList<>();
		BatchRefUpdate bru = refs.newBatchUpdate();
		for (Ref r : all) {
			if (r.isSymbolic()) {
				symrefs.add(r);
			} else {
				bru.addCommand(new ReceiveCommand(ObjectId.zeroId()
						r.getObjectId()
			}
		}

		try (RevWalk rw = new RevWalk(this)) {
			bru.execute(rw
		}

		List<String> failed = new ArrayList<>();
		for (ReceiveCommand cmd : bru.getCommands()) {
			if (cmd.getResult() != ReceiveCommand.Result.OK) {
				failed.add(cmd.getRefName() + ": " + cmd.getResult());
			}
		}

		if (!failed.isEmpty()) {
			throw new IOException(String.format("failed to convert rest: %s"
					StringUtils.join(failed
		}

		for (Ref s : symrefs) {
			RefUpdate up = refs.newUpdate(s.getName()
			up.setForceUpdate(true);
			RefUpdate.Result res = up.link(s.getTarget().getName());
			if (res != RefUpdate.Result.NEW
					&& res != RefUpdate.Result.NO_CHANGE) {
				throw new IOException(
						String.format("ref %s: %s"
			}
		}

		if (!backup) {
			File reftableDir = new File(getDirectory()
			FileUtils.delete(reftableDir
					FileUtils.RECURSIVE | FileUtils.IGNORE_ERRORS);
		}
	}

	@SuppressWarnings("nls")
	void convertToReftable(boolean writeLogs
			throws IOException {
		File newRefs = new File(getDirectory()
		File reftableDir = new File(getDirectory()

		if (reftableDir.exists() && reftableDir.listFiles().length > 0) {
			throw new IOException("reftable dir exists and is nonempty");
		}

		FileReftableDatabase.convertFrom(this

		File refsFile = new File(getDirectory()

		File packedRefs = new File(getDirectory()
		File logsDir = new File(getDirectory()

		if (backup) {
			FileUtils.rename(refsFile
			if (packedRefs.exists()) {
				FileUtils.rename(packedRefs
						Constants.PACKED_REFS + ".old"));
			}
			if (logsDir.exists()) {
				FileUtils.rename(logsDir
						new File(getDirectory()
			}
		} else {
			FileUtils.delete(logsDir
			FileUtils.delete(refsFile
		}

		FileUtils.rename(newRefs

		refs.close();
		refs = new FileReftableDatabase(this
	}

	@SuppressWarnings("nls")
	public void convertRefStorage(String format
			boolean backup) throws IOException {
			if (refs instanceof RefDirectory) {
				convertToReftable(writeLogs
			}
			if (refs instanceof FileReftableDatabase) {
				convertToPackedRefs(backup);
			}
		} else {
			throw new IOException(String.format(
					"unknown supported ref storage format '%s'"
		}
	}

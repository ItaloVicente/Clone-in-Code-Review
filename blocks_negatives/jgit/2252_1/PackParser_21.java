	/**
	 * Rename the pack to it's final name and location and open it.
	 * <p>
	 * If the call completes successfully the repository this IndexPack instance
	 * was created with will have the objects in the pack available for reading
	 * and use, without needing to scan for packs.
	 *
	 * @throws IOException
	 *             The pack could not be inserted into the repository's objects
	 *             directory. The pack no longer exists on disk, as it was
	 *             removed prior to throwing the exception to the caller.
	 */
	public void renameAndOpenPack() throws IOException {
		renameAndOpenPack(null);
	}

	/**
	 * Rename the pack to it's final name and location and open it.
	 * <p>
	 * If the call completes successfully the repository this IndexPack instance
	 * was created with will have the objects in the pack available for reading
	 * and use, without needing to scan for packs.
	 *
	 * @param lockMessage
	 *            message to place in the pack-*.keep file. If null, no lock
	 *            will be created, and this method returns null.
	 * @return the pack lock object, if lockMessage is not null.
	 * @throws IOException
	 *             The pack could not be inserted into the repository's objects
	 *             directory. The pack no longer exists on disk, as it was
	 *             removed prior to throwing the exception to the caller.
	 */
	public PackLock renameAndOpenPack(final String lockMessage)
			throws IOException {
		if (!keepEmpty && entryCount == 0) {
			cleanupTemporaryFiles();
			return null;
		}

		final MessageDigest d = Constants.newMessageDigest();
		final byte[] oeBytes = new byte[Constants.OBJECT_ID_LENGTH];
		for (int i = 0; i < entryCount; i++) {
			final PackedObjectInfo oe = entries[i];
			oe.copyRawTo(oeBytes, 0);
			d.update(oeBytes);
		}

		final String name = ObjectId.fromRaw(d.digest()).name();
		final File packDir = new File(repo.getObjectsDirectory(), "pack");
		final File finalPack = new File(packDir, "pack-" + name + ".pack");
		final File finalIdx = new File(packDir, "pack-" + name + ".idx");
		final PackLock keep = new PackLock(finalPack, repo.getFS());

		if (!packDir.exists() && !packDir.mkdir() && !packDir.exists()) {
			cleanupTemporaryFiles();
			throw new IOException(MessageFormat.format(JGitText.get().cannotCreateDirectory, packDir.getAbsolutePath()));
		}

		if (finalPack.exists()) {
			cleanupTemporaryFiles();
			return null;
		}

		if (lockMessage != null) {
			try {
				if (!keep.lock(lockMessage))
					throw new IOException(MessageFormat.format(JGitText.get().cannotLockPackIn, finalPack));
			} catch (IOException e) {
				cleanupTemporaryFiles();
				throw e;
			}
		}

		if (!dstPack.renameTo(finalPack)) {
			cleanupTemporaryFiles();
			keep.unlock();
			throw new IOException(MessageFormat.format(JGitText.get().cannotMovePackTo, finalPack));
		}

		if (!dstIdx.renameTo(finalIdx)) {
			cleanupTemporaryFiles();
			keep.unlock();
			if (!finalPack.delete())
				finalPack.deleteOnExit();
			throw new IOException(MessageFormat.format(JGitText.get().cannotMoveIndexTo, finalIdx));
		}

		try {
			repo.openPack(finalPack, finalIdx);
		} catch (IOException err) {
			keep.unlock();
			FileUtils.delete(finalPack);
			FileUtils.delete(finalIdx);
			throw err;
		}

		return lockMessage != null ? keep : null;
	}

	private void cleanupTemporaryFiles() {
		if (!dstIdx.delete())
			dstIdx.deleteOnExit();
		if (!dstPack.delete())
			dstPack.deleteOnExit();
	}



	public boolean isModified(boolean forceContentCheck
		if (isAssumeValid())
			return false;

		if (isUpdateNeeded())
			return true;

		File file = new File(wd
		long length = file.length();
		if (length == 0) {
			if (!file.exists())
				return true;
		}
		if (length != getLength())
			return true;

		final int exebits = FileMode.EXECUTABLE_FILE.getBits()
				^ FileMode.REGULAR_FILE.getBits();

		int mode = getRawMode();
		if (checkFilemode && FileMode.EXECUTABLE_FILE.equals(getFileMode())) {
			if (!File_canExecute(file) && File_hasExecute())
				return true;
		} else {
			if (FileMode.REGULAR_FILE.equals(mode & ~exebits)) {
				if (!file.isFile())
					return true;
				if (checkFilemode && File_canExecute(file) && File_hasExecute())
					return true;
			} else {
				if (FileMode.SYMLINK.equals(mode)) {
					return true;
				} else {
					if (FileMode.TREE.equals(mode)) {
						if (!file.isDirectory())
							return true;
					} else {
						System.out.println(MessageFormat.format(
								JGitText.get().doesNotHandleMode
						return true;
					}
				}
			}
		}

		long javamtime = getLastModified() / 1000000L;
		long lastm = file.lastModified();
		if (javamtime % 1000 == 0)
			lastm = lastm - lastm % 1000;
		if (lastm != javamtime) {
			if (!forceContentCheck)
				return true;

			try {
				InputStream is = new FileInputStream(file);
				try {
					ObjectId newId = ow.computeBlobSha1(file.length()
					boolean ret = !newId.equals(getObjectId());
					return ret;
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					try {
						is.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				throw new Error(e);
			}
		}
		return false;
	}

	public boolean isUpdateNeeded() {
		return (info[infoOffset + P_FLAGS] & UPDATE_NEEDED) != 0;
	}

	static boolean File_canExecute(File f){
		return FS.DETECTED.canExecute(f);
	}

	static boolean File_setExecute(File f
		return FS.DETECTED.setExecute(f
	}

	static boolean File_hasExecute() {
		return FS.DETECTED.supportsExecute();
	}

	public void checkoutEntry(Repository repo
			throws IOException {
		ObjectLoader ol = repo.openBlob(getObjectId());
		byte[] bytes = ol.getBytes();
		f.delete();
		f.getParentFile().mkdirs();
		FileChannel channel = new FileOutputStream(f).getChannel();
		ByteBuffer buffer = ByteBuffer.wrap(bytes);
		int j = channel.write(buffer);
		if (j != bytes.length)
			throw new IOException(MessageFormat.format(
					JGitText.get().couldNotWriteFile
		channel.close();
		if (config_filemode && File_hasExecute()) {
			if (FileMode.EXECUTABLE_FILE.equals(getFileMode())) {
				if (!File_canExecute(f))
					File_setExecute(f
			} else {
				if (File_canExecute(f))
					File_setExecute(f
			}
		}
		setLastModified(f.lastModified() * 1000000L);
	}


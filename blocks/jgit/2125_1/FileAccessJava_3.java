
	@Override
	public String readlink(File file) throws UnsupportedOperationException
			NoSuchFileException

		try {
			return RawParseUtils.decode(IO.readFully(file));
		} catch (FileNotFoundException e) {
			throw new NoSuchFileException(file.getPath());
		} catch (IOException err) {
			throw new AccessDeniedException(err.getMessage());
		}
	}

	@Override
	public void symlink(File file
			throws UnsupportedOperationException

		if (file.exists())
			throw new FileExistsException(file.getPath());

		try {
			boolean ok = false;
			File dir = file.getParentFile();
			File tmp = File.createTempFile("._" + file.getName()
			try {
				FileOutputStream out = new FileOutputStream(tmp);
				try {
					out.write(target.getBytes());
				} finally {
					out.close();
				}
				if (tmp.renameTo(file))
					ok = true;
				else
					throw new IOException();
			} finally {
				if (!ok)
					tmp.delete();
			}
		} catch (IOException err) {
			throw new NativeException(err.getMessage()
		}
	}

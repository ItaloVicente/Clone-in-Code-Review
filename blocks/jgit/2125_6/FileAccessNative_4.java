	public String readlink(File file) throws UnsupportedOperationException
			AccessDeniedException
		return readlinkImp(file.getPath());
	}

	public void symlink(File file
			throws UnsupportedOperationException
			NoSuchFileException
		symlinkImp(file.getPath()
	}


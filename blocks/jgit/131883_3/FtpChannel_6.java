	default void delete(String path) throws IOException {
		try {
			rm(path);
		} catch (FileNotFoundException e) {
		} catch (FtpException f) {
			if (f.getStatus() == FtpException.NO_SUCH_FILE) {
				return;
			}
			throw f;
		}
	}


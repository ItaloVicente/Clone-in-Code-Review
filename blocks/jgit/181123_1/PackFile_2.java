	@SuppressWarnings("nls")
	public synchronized boolean exists() throws IOException {
		RandomAccessFile fdOrig = fd;
		try {
			if (fdOrig == null) {
				doOpen();
			}
			read(0
			return true;
		} catch (PackInvalidException | FileNotFoundException e) {
			LOG.warn("Packfile {} is not accessible"
			return false;
		} catch (IOException e) {
			if (FileUtils.isStaleFileHandle(e)
					|| FileUtils.isStaleFileHandleInCausalChain(e)) {
				LOG.warn("Packfile {} is pointing to a stale file handle"
						packFile
				return false;
			}
			throw e;
		} finally {
			if (fdOrig == null) {
				doClose();
			}
		}
	}


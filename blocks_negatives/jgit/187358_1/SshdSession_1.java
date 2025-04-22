				try (CloseableHandle handle = ftp.openDir(absolute(path))) {
					AtomicReference<Boolean> atEnd = new AtomicReference<>(
							Boolean.FALSE);
					while (!atEnd.get().booleanValue()) {
						List<SftpClient.DirEntry> chunk = ftp.readDir(handle,
								atEnd);
						if (chunk == null) {
							break;

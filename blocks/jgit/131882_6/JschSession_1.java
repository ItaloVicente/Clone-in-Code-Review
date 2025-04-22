
	private class JschFtpChannel implements FtpChannel {

		private ChannelSftp ftp;

		@Override
		public void connect(int timeout
			try {
				ftp.connect((int) unit.toMillis(timeout));
			} catch (JSchException e) {
				ftp = null;
				throw new IOException(e.getLocalizedMessage()
			}
		}

		@Override
		public void disconnect() {
			ftp.disconnect();
			ftp = null;
		}

		private <T> T map(Callable<T> op) throws IOException {
			try {
				return op.call();
			} catch (Exception e) {
				if (e instanceof SftpException) {
					throw new FtpChannel.FtpException(e.getLocalizedMessage()
							((SftpException) e).id
				}
				throw new IOException(e.getLocalizedMessage()
			}
		}

		@Override
		public boolean isConnected() {
			return ftp != null && sock.isConnected();
		}

		@Override
		public void cd(String path) throws IOException {
			map(() -> {
				ftp.cd(path);
				return null;
			});
		}

		@Override
		public String pwd() throws IOException {
			return map(() -> ftp.pwd());
		}

		@Override
		public Collection<DirEntry> ls(String path) throws IOException {
			return map(() -> {
				List<DirEntry> result = new ArrayList<>();
				for (Object e : ftp.ls(path)) {
					ChannelSftp.LsEntry entry = (ChannelSftp.LsEntry) e;
					result.add(new DirEntry() {

						@Override
						public String getFilename() {
							return entry.getFilename();
						}

						@Override
						public long getModifiedTime() {
							return entry.getAttrs().getMTime();
						}

						@Override
						public boolean isDirectory() {
							return entry.getAttrs().isDir();
						}
					});
				}
				return result;
			});
		}

		@Override
		public void rmdir(String path) throws IOException {
			map(() -> {
				ftp.rm(path);
				return null;
			});
		}

		@Override
		public void mkdir(String path) throws IOException {
			map(() -> {
				ftp.mkdir(path);
				return null;
			});
		}

		@Override
		public InputStream get(String path) throws IOException {
			return map(() -> ftp.get(path));
		}

		@Override
		public OutputStream put(String path) throws IOException {
			return map(() -> ftp.put(path));
		}

		@Override
		public void rm(String path) throws IOException {
			map(() -> {
				ftp.rm(path);
				return null;
			});
		}

		@Override
		public void rename(String from
			map(() -> {
				ftp.rename(from
				return null;
			});
		}

	}

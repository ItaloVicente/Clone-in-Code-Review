						for (SftpClient.DirEntry remote : chunk) {
							result.add(new DirEntry() {

								@Override
								public String getFilename() {
									return remote.getFilename();
								}

								@Override
								public long getModifiedTime() {
									return remote.getAttributes()
											.getModifyTime().toMillis();
								}

								@Override
								public boolean isDirectory() {
									return remote.getAttributes().isDirectory();
								}

							});

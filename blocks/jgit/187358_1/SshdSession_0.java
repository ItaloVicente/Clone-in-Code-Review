				for (SftpClient.DirEntry remote : ftp.readDir(absolute(path))) {
					result.add(new DirEntry() {

						@Override
						public String getFilename() {
							return remote.getFilename();

					try {
						ftp.mkdir(path);
						return;
					} catch (SftpException je2) {
						je = je2;
					}

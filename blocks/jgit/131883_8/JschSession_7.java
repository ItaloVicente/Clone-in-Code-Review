				if (hasPosixRename()) {
					ftp.rename(from
				} else if (!to.equals(from)) {
					delete(to);
					ftp.rename(from
				}

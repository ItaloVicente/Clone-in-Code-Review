				FS fs = local != null ? local.getFS() : FS.DETECTED;
				File f;
					f = fs.resolve(fs.userHome()
				} else {
					f = new File(path);
					if (!f.isAbsolute()) {
						f = fs.resolve(null
						LOG.warn(MessageFormat.format(
								JGitText.get().cookieFilePathRelative
					}
				}

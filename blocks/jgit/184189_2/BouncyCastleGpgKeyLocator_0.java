		Function<String
				return new File(FS.DETECTED.userHome()
						.getAbsoluteFile().toPath();
			}
			return Paths.get(s);
		};
		Path path = checkDirectory(system.getProperty("jgit.gpg.home")
				resolveTilde
				s -> log.warn(BCText.get().logWarnGpgHomeProperty
		if (path != null) {
			return path;
		}
		path = checkDirectory(system.getenv("GNUPGHOME")
				s -> log.warn(BCText.get().logWarnGnuPGHome
		if (path != null) {
			return path;
		}

		File toInclude = new File(line.value);
			File userHome = FS.DETECTED.userHome();
			if (userHome != null) {
				toInclude = new File(userHome
						toInclude.getPath().substring(2));
			}

		}


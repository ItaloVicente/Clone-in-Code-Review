			if (isFile(u))
				scheme.select(S_FILE);
			else if (isSSH(u))
				scheme.select(S_SSH);
			else {
				for (int i = 0; i < DEFAULT_SCHEMES.length; i++) {
					if (DEFAULT_SCHEMES[i].equals(u.getScheme())) {
						scheme.select(i);
						break;
					}
				}
			}

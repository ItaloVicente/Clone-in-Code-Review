						if (version == null) {
							version ="";
						} else {
							if (E4_DARK_THEME_ID.equals(id) && Platform.OS_MACOSX.equals(currentOS) && os != null
									&& os.equals(currentOS)) {
								if (!isOsVersionMatch(version)) {
									continue;
								} else {
									version = "";
								}

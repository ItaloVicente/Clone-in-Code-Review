					if (ce.getName().equals("theme")) {
						try {
							String id = ce.getAttribute("id");
							String os = ce.getAttribute("os");
							String version = ce.getAttribute("os_version");

							/*
							 * Code to support e4 dark theme on Mac 10.13 and older. For e4 dark theme on
							 * Mac, register the theme with matching OS version if specified.
							 */
							if (E4_DARK_THEME_ID.equals(id) && Platform.OS_MACOSX.equals(currentOS) && os != null
									&& os.equals(currentOS)) {
								if (e4_dark_mac_found) {
									continue;
								}
								if (version != null && !isOsVersionMatch(version)) {
									continue;
								} else {
									e4_dark_mac_found = true;
									version = "";
								}

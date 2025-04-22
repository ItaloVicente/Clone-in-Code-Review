			for (IConfigurationElement ce : getPlatformMatches(e.getConfigurationElements())) {
					if (ce.getName().equals("theme")) {
						try {
							String id = ce.getAttribute("id");
							String os = ce.getAttribute("os");
							String version = ce.getAttribute("os_version");

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

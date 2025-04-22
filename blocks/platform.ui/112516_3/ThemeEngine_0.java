						String label = ce.getAttribute("label");
						System.out.println(Platform.getOS());
						String os = ce.getAttribute("os");
						if (os != null && !os.equals(currentOS)) {
							if (!themesToOSVarients.containsKey(themeId)) {
								themesToOSVarients.put(themeId, new ArrayList<>());
							}
							themesToOSVarients.get(themeId).add(os);
							themeId = getOsSpecificThemeId(themeId, os);
							label = getOsSpecificThemeLabel(label, os);
						}

						String label = ce.getAttribute("label");
						String os = ce.getAttribute("os");
						if (os != null && !os.contains(osname)) {
							if (oSsWithThemeVarient.get(themeId) == null) {
								ArrayList<String> osArrayList = new ArrayList<>();
								osArrayList.add(ce.getAttribute("os"));
								oSsWithThemeVarient.put(themeId, osArrayList);
							} else {
								oSsWithThemeVarient.get(themeId).add(os);
							}
							themeId = getOsSpecificThemeId(themeId, os);
							label = getOsSpecificThemeLabel(label, os);
						}

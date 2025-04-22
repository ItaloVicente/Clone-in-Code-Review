						final String themeBaseId = ce.getAttribute("id") + version;
						String themeId = themeBaseId;
						String label = ce.getAttribute("label");
						String os = ce.getAttribute("os");
						String ws = ce.getAttribute("ws");
						if ((os != null && !os.equals(currentOS)) || (ws != null && !ws.equals(currentWS))) {
							if (!themesToVarients.containsKey(themeBaseId)) {
								themesToVarients.put(themeBaseId, new ArrayList<>());
							}
							themeId = getVarientThemeId(themeBaseId, os, ws);
							themesToVarients.get(themeBaseId).add(themeId);
							label = getVarientThemeLabel(label, os, ws);
						}

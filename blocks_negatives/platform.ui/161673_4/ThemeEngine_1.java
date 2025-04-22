						final String themeBaseId = id + version;
						String themeId = themeBaseId;
						String label = ce.getAttribute("label");
						String ws = ce.getAttribute("ws");
						if ((os != null && !os.equals(currentOS)) || (ws != null && !ws.equals(currentWS))) {
							if (!themesToVarients.containsKey(themeBaseId)) {
								themesToVarients.put(themeBaseId, new ArrayList<>());

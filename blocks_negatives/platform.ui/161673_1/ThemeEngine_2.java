							themeId = getVarientThemeId(themeBaseId, os, ws);
							themesToVarients.get(themeBaseId).add(themeId);
							label = getVarientThemeLabel(label, os, ws);
						}
						String originalCSSFile;
						String basestylesheeturi = originalCSSFile = ce.getAttribute("basestylesheeturi");
						if (!basestylesheeturi.startsWith("platform:/plugin/")) {
							basestylesheeturi = "platform:/plugin/" + ce.getContributor().getName() + "/"
									+ basestylesheeturi;
						}
						registerTheme(themeId, label, basestylesheeturi, version);

						if (modifiedFiles != null) {
							int slash = originalCSSFile.lastIndexOf('/');
							if (slash != -1) {
								originalCSSFile = originalCSSFile.substring(slash + 1);
								for (File modifiedFile : modifiedFiles) {
									String modifiedFileName = modifiedFile.getName();
										ArrayList<String> styleSheets = new ArrayList<>();
										styleSheets.add(modifiedFile.toURI().toString());
										modifiedStylesheets.put(themeId, styleSheets);

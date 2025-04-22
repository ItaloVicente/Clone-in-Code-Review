						String originalCSSFile;
						String basestylesheeturi = originalCSSFile = ce.getAttribute("basestylesheeturi");
						if (!basestylesheeturi.startsWith("platform:/plugin/")) {
							basestylesheeturi = "platform:/plugin/" + ce.getContributor().getName() + "/"
									+ basestylesheeturi;
						}

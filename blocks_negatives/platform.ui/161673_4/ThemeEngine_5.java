							themes.add(refid);
						}
						registerStylesheet(
								"platform:/plugin/" + ce.getContributor().getName() + "/" + ce.getAttribute("uri"),
								themes.toArray(new String[themes.size()]));
						for (IConfigurationElement resourceEl : ce
								.getChildren("osgiresourcelocator")) {
							String uri = resourceEl.getAttribute("uri");
							if (uri != null) {
								registerResourceLocator(new OSGiResourceLocator(
										uri));

							registerStylesheet(
									"platform:/plugin/" + ce.getContributor().getName() + "/" + ce.getAttribute("uri"),
									themes);

							for (IConfigurationElement resourceEl : ce
									.getChildren("osgiresourcelocator")) {
								String uri = resourceEl.getAttribute("uri");
								if (uri != null) {
									registerResourceLocator(new OSGiResourceLocator(
											uri));
								}

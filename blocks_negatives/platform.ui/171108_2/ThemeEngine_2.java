			for (IExtension e : extPoint.getExtensions()) {
				for (IConfigurationElement ce : getPlatformMatches(e.getConfigurationElements())) {
					if (ce.getName().equals("stylesheet")) {
						IConfigurationElement[] cces = ce.getChildren("themeid");
						if (cces.length == 0) {
							registerStylesheet("platform:/plugin/"
									+ ce.getContributor().getName() + "/"
									+ ce.getAttribute("uri"));

							for (IConfigurationElement resourceEl : ce
									.getChildren("osgiresourcelocator")) {
								String uri = resourceEl.getAttribute("uri");
								if (uri != null) {
									registerResourceLocator(new OSGiResourceLocator(
											uri));
								}
							}
						} else {
							String[] themes = new String[cces.length];
							for (int i = 0; i < cces.length; i++) {
								themes[i] = cces[i].getAttribute("refid");

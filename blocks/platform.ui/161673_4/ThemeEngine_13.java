		private IConfigurationElement[] getPlatformMatches(IConfigurationElement[] elements) {
			Bundle bundle = FrameworkUtil.getBundle(ThemeEngine.class);
			String osname = bundle.getBundleContext().getProperty("osgi.os");
			String wsname = bundle.getBundleContext().getProperty("osgi.ws");
			ArrayList<IConfigurationElement> matchingElements = new ArrayList<>();
			for (IConfigurationElement element : elements) {
				String elementOs = element.getAttribute("os");
				String elementWs = element.getAttribute("ws");
				if (osname != null && (elementOs == null || elementOs.contains(osname))) {
					matchingElements.add(element);
				} else if (wsname != null && wsname.equalsIgnoreCase(elementWs)) {
					matchingElements.add(element);
				}
			}
			return matchingElements.toArray(new IConfigurationElement[matchingElements.size()]);
		}

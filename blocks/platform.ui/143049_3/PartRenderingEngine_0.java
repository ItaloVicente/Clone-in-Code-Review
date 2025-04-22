				Set<String> contributorNames = new TreeSet<>();
				for (IConfigurationElement element : RegistryFactory.getRegistry()
						.getConfigurationElementsFor("org.eclipse.ui.themes")) {
					contributorNames.add(element.getContributor().getName());
				}
				for (IConfigurationElement element : RegistryFactory.getRegistry()
						.getConfigurationElementsFor("org.eclipse.e4.ui.css.swt.theme")) {
					contributorNames.add(element.getContributor().getName());
				}
				for (String contributorName : contributorNames) {
					prefs.add(InstanceScope.INSTANCE.getNode(contributorName));

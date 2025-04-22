			for (IConfigurationElement ce : registry.getConfigurationElementsFor(ELEMENT_PROVIDER_EXTPOINT)) {
				if ("provider".equals(ce.getName())) {
					for (IConfigurationElement ce2 : ce.getChildren()) {
						if (typeName.equals(ce2.getAttribute("class"))) {
							try {
								provider = (IElementProvider) ce.createExecutableExtension("class");
								providerCache.put(o.getClass(), provider);
								return provider.getElement(o, engine);
							} catch (CoreException e1) {
								e1.printStackTrace();

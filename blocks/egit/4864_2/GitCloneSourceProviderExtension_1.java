			IConfigurationElement serverProviderElement = null;
			if (myIndex < config.length
					&& config[myIndex].getName().equals("repositoryServerProvider")) { //$NON-NLS-1$
				serverProviderElement = config[myIndex];
				myIndex++;
			}
			IConfigurationElement pageElement = null;
			if (myIndex < config.length
					&& config[myIndex].getName().equals("repositorySearchPage")) { //$NON-NLS-1$
				pageElement = config[myIndex];
				myIndex++;
			}
			cloneSourceProvider.add(new CloneSourceProvider(label,
					serverProviderElement, pageElement, hasFixLocation, icon));
			if (myIndex == config.length)
				return;
			addCloneSourceProvider(cloneSourceProvider, config, myIndex);
		} catch (Exception e) {
			Activator.logError("Could not create extension provided by " + //$NON-NLS-1$
					Platform.getBundle(config[index].getDeclaringExtension().getNamespaceIdentifier()), e);

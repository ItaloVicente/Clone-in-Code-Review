			IConfigurationElement[] allGroups = new IConfigurationElement[filterGroups.length
					+ extendedElements.size()];
			System
					.arraycopy(filterGroups, 0, allGroups, 0,
							filterGroups.length);
			Iterator extras = extendedElements.iterator();

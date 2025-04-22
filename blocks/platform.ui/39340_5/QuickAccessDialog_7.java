
			Map<String, QuickAccessProvider> providerMap = new HashMap<String, QuickAccessProvider>();
			for (QuickAccessProvider provider : contents.getProviders()) {
				providerMap.put(provider.getId(), provider);
			}


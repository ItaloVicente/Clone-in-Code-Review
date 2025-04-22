		for (int i = 0; i < extensions.length; i++) {
			IConfigurationElement[] children = extensions[i]
					.getConfigurationElements();
			for (int j = 0; j < children.length; j++) {
				AbstractSplashHandler handler = processElement(children[j],
						idToSplash, targetId, product);

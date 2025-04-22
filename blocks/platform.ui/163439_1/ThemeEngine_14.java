			}
			for (String stylesheet : getAllStyles(theme.getId())) {
				URL url;
				InputStream stream = null;
				try {
					url = FileLocator.resolve(new URL(stylesheet));

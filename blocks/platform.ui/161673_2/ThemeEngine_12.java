		private List<IResourceLocator> getResourceLocators(String id) {
			List<IResourceLocator> list = new ArrayList<>(
					globalSourceLocators);
			List<IResourceLocator> s = sourceLocators.get(id);
			if (s != null) {
				list.addAll(s);
			}

			return list;

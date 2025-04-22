			if (parentElement instanceof ThemeElementCategory) {
				String categoryId = ((ThemeElementCategory) parentElement).getId();
				Object[] defintions = (Object[]) categoryMap.get(categoryId);
				if (defintions == null) {
					defintions = getCategoryChildren(categoryId);
					categoryMap.put(categoryId, defintions);
				}
				return defintions;
			}

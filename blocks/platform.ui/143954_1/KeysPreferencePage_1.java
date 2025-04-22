			for (Iterator<Map.Entry<String, Set<Category>>> iterator = categoriesByName.entrySet().iterator(); iterator
					.hasNext();) {
				Map.Entry<String, Set<Category>> entry = iterator.next();
				String name = entry.getKey();
				Set<Category> categories = entry.getValue();
				Iterator<Category> iterator2 = categories.iterator();

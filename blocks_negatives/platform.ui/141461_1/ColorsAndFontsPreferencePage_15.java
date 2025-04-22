				for (int i = 0; i < fontDefinitions.length; i++) {
					if (!fontDefinitions[i].isEditable()) {
						continue;
					}
					String catId = fontDefinitions[i].getCategoryId();
					if ((catId == null && categoryId == null)
							|| (catId != null && categoryId != null && categoryId.equals(catId))) {
						if (fontDefinitions[i].getDefaultsTo() != null && parentIsInSameCategory(fontDefinitions[i])) {
							continue;
						}
						list.add(fontDefinitions[i]);
					}

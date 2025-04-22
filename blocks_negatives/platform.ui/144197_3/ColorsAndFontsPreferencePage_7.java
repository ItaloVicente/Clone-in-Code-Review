					String catId = colorDefinition.getCategoryId();
					if ((catId == null && categoryId == null)
							|| (catId != null && categoryId != null && categoryId.equals(catId))) {
						if (colorDefinition.getDefaultsTo() != null && parentIsInSameCategory(colorDefinition)) {
							continue;
						}
						list.add(colorDefinition);
					}

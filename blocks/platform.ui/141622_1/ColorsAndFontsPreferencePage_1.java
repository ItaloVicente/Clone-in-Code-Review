				String catId = fontDefinition.getCategoryId();
				if ((catId == null && categoryId == null)
					|| (catId != null && categoryId != null && categoryId.equals(catId))) {
				    if (fontDefinition.getDefaultsTo() != null && parentIsInSameCategory(fontDefinition)) {
					continue;
				    }
				    list.add(fontDefinition);
				}
			    }

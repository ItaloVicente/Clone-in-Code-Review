						String uniqueName = MessageFormat.format(
								Util.translateString(RESOURCE_BUNDLE,
										"uniqueName"), new Object[] { name, //$NON-NLS-1$
										category.getId() });
						categoryIdsByUniqueName.put(uniqueName, category
								.getId());
						categoryUniqueNamesById.put(category.getId(),
								uniqueName);

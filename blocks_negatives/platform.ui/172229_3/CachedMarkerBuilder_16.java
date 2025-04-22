					MarkerGroup newGroup = getGenerator().getMarkerGroup(categoryGroupID);
					if (newGroup == null) {
						setDefaultCategoryGroup(getGenerator());
					} else {
						this.categoryGroup = newGroup;
					}

			}
		});
		category.addCategoryListener(new ICategoryListener() {

			@Override
			public void categoryChanged(CategoryEvent categoryEvent) {
				System.err.println("categoryChanged");
				registryChanged[1] = true;

			}

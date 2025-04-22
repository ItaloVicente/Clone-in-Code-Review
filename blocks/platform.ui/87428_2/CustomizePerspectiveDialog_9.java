				.addSelectionChangedListener(event -> {
					Category category = (Category) ((IStructuredSelection) event
							.getSelection()).getFirstElement();
					menuCategoriesViewer.setInput(category);
					menuItemsViewer.setInput(category);
					if (category.getChildrenCount() != 0) {
						setSelectionOn(menuCategoriesViewer, category
								.getChildren().get(0));

		menusViewer.addSelectionChangedListener(event -> {
			Category category = (Category) event.getStructuredSelection().getFirstElement();
			menuCategoriesViewer.setInput(category);
			menuItemsViewer.setInput(category);
			if (category.getChildrenCount() != 0) {
				setSelectionOn(menuCategoriesViewer, category.getChildren().get(0));
			}
		});

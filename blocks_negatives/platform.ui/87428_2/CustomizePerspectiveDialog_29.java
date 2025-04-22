		menuCategoriesViewer.addCheckStateListener(new ICheckStateListener() {
			@Override
			public void checkStateChanged(CheckStateChangedEvent event) {
				Category category = (Category) event.getElement();
				category.setItemsState(event.getChecked());
				updateCategoryAndParents(menuCategoriesViewer, category);
			}

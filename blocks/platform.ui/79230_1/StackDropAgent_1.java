
		if (hiddenTabs) {
			int nVisibleItems = vItems.size();
			if(dropIndex<nVisibleItems){
				CTabItem item = vItems.get(dropIndex);
				MUIElement itemModel = (MUIElement) item.getData(AbstractPartRenderer.OWNING_ME);

				if (itemModel == dragElement)
					return;
				dropIndex = itemModel.getParent().getChildren().indexOf(itemModel);
			} else if (dropIndex == nVisibleItems) {
				dropIndex = dropStack.getChildren().size();
			}


		} else {
			int ctfItemCount = dropCTF.getItemCount();
			if (dropIndex < ctfItemCount) {
				CTabItem item = dropCTF.getItem(dropIndex);
				MUIElement itemModel = (MUIElement) item.getData(AbstractPartRenderer.OWNING_ME);

				if (itemModel == dragElement)
					return;

				dropIndex = itemModel.getParent().getChildren().indexOf(itemModel);
			} else if (dropIndex == ctfItemCount) {
				dropIndex = dropStack.getChildren().size();
			}

			kids.remove(curSel);
			dropIndex = dropIndex + selIndex;
			if (dropIndex >= 0 && dropIndex < dropStack.getChildren().size())
				dropStack.getChildren().add(dropIndex, curSel);
			else
				dropStack.getChildren().add(curSel);


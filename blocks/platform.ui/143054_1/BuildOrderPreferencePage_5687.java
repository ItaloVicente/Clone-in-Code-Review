		this.workbench = workbench;
		setPreferenceStore(PrefUtil.getInternalPreferenceStore());
	}

	private void moveSelectionDown() {

		if (this.buildList.getSelectionCount() == 1) {
			int currentIndex = this.buildList.getSelectionIndex();
			if (currentIndex < this.buildList.getItemCount() - 1) {
				String elementToMove = this.buildList.getItem(currentIndex);
				this.buildList.remove(currentIndex);
				this.buildList.add(elementToMove, currentIndex + 1);
				this.buildList.select(currentIndex + 1);
			}
		}
	}

	private void moveSelectionUp() {

		int currentIndex = this.buildList.getSelectionIndex();

		if (currentIndex > 0 && this.buildList.getSelectionCount() == 1) {
			String elementToMove = this.buildList.getItem(currentIndex);
			this.buildList.remove(currentIndex);
			this.buildList.add(elementToMove, currentIndex - 1);
			this.buildList.select(currentIndex - 1);
		}
	}

	@Override

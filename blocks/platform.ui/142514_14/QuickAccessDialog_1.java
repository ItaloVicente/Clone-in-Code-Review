	private void linkTexts() {
		if (linkedText == null) {
			return;
		}
		toRemoveTextListeners = new HashSet<>();

		ModifyListener filterUpdatesLinkedText = e -> {
			if (!Objects.equals(this.filterText.getText(), this.linkedText.getText())) {
				linkedText.setText(filterText.getText());
			}
		};
		filterText.addModifyListener(filterUpdatesLinkedText);
		toRemoveTextListeners.add(filterUpdatesLinkedText);

		ModifyListener linkedUpdateFilterText = e -> {
			if (!Objects.equals(this.filterText.getText(), this.linkedText.getText())) {
				filterText.setText(linkedText.getText());
			}
		};
		linkedText.addModifyListener(linkedUpdateFilterText);
		toRemoveTextListeners.add(linkedUpdateFilterText);
	}


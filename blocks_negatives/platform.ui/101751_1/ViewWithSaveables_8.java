		private IValueChangeListener valueChangeListener = new IValueChangeListener() {
			@Override
			public void handleValueChange(ValueChangeEvent event) {
				fireMapChange(Diffs.createMapDiffSingleChange(
						writableValueToElement.get(event.getSource()),
						event.diff.getOldValue(), event.diff.getNewValue()));
			}
		};

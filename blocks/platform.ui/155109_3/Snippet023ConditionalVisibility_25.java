		ISideEffect.create(() -> {
			boolean isRange = rangeSelected.getValue();
			boolean isText = textSelected.getValue();
			if (isRange) {
				stackLayout.topControl = rangeGroup;
				oneOfTwo.layout();
			} else if (isText) {
				stackLayout.topControl = textGroup;
				oneOfTwo.layout();

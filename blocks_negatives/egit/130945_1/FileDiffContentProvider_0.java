			viewer.refresh();
			FileDiff interesting = getFirstInterestingElement();
			if (interesting != null) {
				if (currentInput.isSelectMarked()) {
					viewer.setSelection(new StructuredSelection(interesting),
							true);
				} else {
					viewer.reveal(interesting);

			int changeNumber = change.getChangeNumber().intValue();
			for (Change fromGerrit : changes) {
				int num = fromGerrit.getChangeNumber().intValue();
				if (num < changeNumber) {
				} else if (changeNumber == num) {
					String fullRef = fromGerrit.getRefName();
					refText.setText(fullRef);
					refText.setSelection(fullRef.length());
					return;
				}

			Change fromGerrit = findHighestPatchSet(changes,
					change.getChangeNumber().intValue());
			if (fromGerrit != null) {
				String fullRef = fromGerrit.getRefName();
				refText.setText(fullRef);
				refText.setSelection(fullRef.length());

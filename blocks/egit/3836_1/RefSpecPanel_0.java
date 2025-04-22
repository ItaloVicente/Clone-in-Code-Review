		if (pushSpecs) {
			String newDst = src;
			newDst = deleteTagPrefix(newDst);
			newDst = deleteHeadsPrefix(newDst);
			creationDstCombo.setText(newDst);
		}

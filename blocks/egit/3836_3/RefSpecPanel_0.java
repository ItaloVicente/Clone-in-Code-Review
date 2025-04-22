		if (pushSpecs) {
			String newDst = src;
			newDst = deletePrefixes(src,
					Constants.R_TAGS.substring(Constants.R_REFS.length()),
					Constants.R_HEADS.substring(Constants.R_REFS.length()));
			creationDstCombo.setText(newDst);
		}

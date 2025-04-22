			Styler boldStyler = new Styler() {
				@Override
				public void applyStyles(TextStyle textStyle) {
					textStyle.font = JFaceResources.getFontRegistry().getBold(JFaceResources.DEFAULT_FONT);
				}
			};

			int potentialIndex = str.getString().toLowerCase().indexOf(searchFieldString.toLowerCase());
			if (potentialIndex != -1) {
				str.setStyle(potentialIndex, searchFieldString.length(), boldStyler);
			} else if (searchFieldString.indexOf('?') != -1 || searchFieldString.indexOf('*') != -1) {
				str = markRegions(str, searchFieldString, boldStyler, false);
			} else {
				final String wildcard = "*"; //$NON-NLS-1$
				String matchingString = String.join(wildcard, searchFieldString.split("(?=[A-Z])")) + wildcard; //$NON-NLS-1$
				str = markRegions(str, matchingString, boldStyler, true);

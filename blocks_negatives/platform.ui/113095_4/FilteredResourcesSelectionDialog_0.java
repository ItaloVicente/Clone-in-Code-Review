
			int potentialIndex = str.getString().toLowerCase().indexOf(searchFieldString.toLowerCase());
			if (potentialIndex != -1) {
				str.setStyle(potentialIndex, searchFieldString.length(), boldStyler);
			} else if (searchFieldString.indexOf('?') != -1 || searchFieldString.indexOf('*') != -1) {
				str = markRegions(str, String.join(wildcard, searchFieldString.split("(?=[\\.])")), boldStyler); //$NON-NLS-1$
			} else {
				String matchingString = String.join(wildcard, searchFieldString.split("(?=[A-Z\\.])")) + wildcard; //$NON-NLS-1$
				str = markRegions(str, matchingString, boldStyler);

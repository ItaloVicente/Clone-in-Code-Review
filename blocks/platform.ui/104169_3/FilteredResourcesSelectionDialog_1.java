			return str;
		}

		private StyledString markRegions(StyledString styledString, String matchingString, Styler styler,
				boolean caseSensitive) {
			String text = styledString.getString();
			if (!caseSensitive) {
				text = text.toLowerCase();
				matchingString = matchingString.toLowerCase();
			}
			StyledString updatedText = styledString;
			int startingIndex = 0;
			int currentIndex = 0;
			String[] regions = matchingString.split("(\\?)|\\*"); //$NON-NLS-1$
			boolean restart = false;

			do {
				for (String region : regions) {
					if (region == null || region.isEmpty()) {
						continue;
					} else if (region.equals("?")) { //$NON-NLS-1$
						currentIndex++;
					} else {
						int startlocation = indexOf(Pattern.compile(region), text.substring(currentIndex));
						if (startlocation == -1) {
							currentIndex = ++startingIndex;
							updatedText = styledString;
							restart = true;
							break;
						}
						updatedText.setStyle(startlocation + currentIndex, region.length(), styler);
						currentIndex += startlocation + region.length();
					}
				}
			} while (restart && currentIndex < text.length());

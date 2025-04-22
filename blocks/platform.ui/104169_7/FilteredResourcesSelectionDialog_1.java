			return str;
		}

		private StyledString markRegions(StyledString styledString, String matchingString, Styler styler) {
			String text = styledString.getString().toLowerCase();
			matchingString = matchingString.toLowerCase();
			StyledString updatedText = styledString;
			int startingIndex = 0;
			int currentIndex = 0;
			String[] regions = matchingString.replaceAll("\\.", "\\\\.").split("(\\?)|\\*"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			boolean restart = false;

			do {
				for (String region : regions) {
					if (region == null || region.isEmpty()) {
						continue;
					} else if (region.equals("?")) { //$NON-NLS-1$
						currentIndex++;
					} else {
						int startlocation = indexOf(Pattern.compile(region), text.substring(currentIndex));
						int length = region.replace("\\", "").length(); //$NON-NLS-1$ //$NON-NLS-2$
						if (startlocation == -1) {
							currentIndex = ++startingIndex;
							updatedText = styledString;
							restart = true;
							break;
						}
						updatedText.setStyle(startlocation + currentIndex, length, styler);
						currentIndex += startlocation + length;
					}
				}
			} while (restart && currentIndex < text.length());

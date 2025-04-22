					} else {
						int startlocation = indexOf(Pattern.compile(region), text.substring(currentIndex));
						int length = region.replace("\\", "").length(); //$NON-NLS-1$ //$NON-NLS-2$
						if (startlocation == -1) {
							currentIndex = ++startingIndex;
							updatedText = styledString;
							restart = true;
							break;

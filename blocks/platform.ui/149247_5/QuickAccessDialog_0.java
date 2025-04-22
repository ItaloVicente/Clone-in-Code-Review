					String firstText = null;
					if (inputTextIndex < userInputTexts.length && numberOfMatchingTextsForCurrentElement >= 1) {
						firstText = userInputTexts[inputTextIndex];
					}
					QuickAccessElement quickAccessElement = quickAccessProvider.findElement(orderedElements[i],
							firstText);

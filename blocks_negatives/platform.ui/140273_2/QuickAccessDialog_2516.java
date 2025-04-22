						/**
						 * @param element
						 */
						void addPreviousPick(String text, QuickAccessElement element) {
							previousPicksList.remove(element);
							if (previousPicksList.size() == MAXIMUM_NUMBER_OF_ELEMENTS) {
								QuickAccessElement removedElement = previousPicksList.removeLast();
								ArrayList<String> removedList = textMap
										.remove(removedElement);
								for (int i = 0; i < removedList.size(); i++) {
									elementMap.remove(removedList.get(i));
								}
							}
							previousPicksList.addFirst(element);

							ArrayList<String> textList = textMap.get(element);
							if (textList == null) {
								textList = new ArrayList<>();
								textMap.put(element, textList);
							}

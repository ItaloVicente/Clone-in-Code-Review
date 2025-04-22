							/**
							 * @param element
							 */
							void addPreviousPick(String text, Object element) {
								previousPicksList.remove(element);
								if (previousPicksList.size() == MAXIMUM_NUMBER_OF_ELEMENTS) {
									Object removedElement = previousPicksList.removeLast();
									ArrayList removedList = (ArrayList) textMap
											.remove(removedElement);
									for (int i = 0; i < removedList.size(); i++) {
										elementMap.remove(removedList.get(i));
									}
								}
								previousPicksList.addFirst(element);

								ArrayList textList = (ArrayList) textMap.get(element);
								if (textList == null) {
									textList = new ArrayList();
									textMap.put(element, textList);

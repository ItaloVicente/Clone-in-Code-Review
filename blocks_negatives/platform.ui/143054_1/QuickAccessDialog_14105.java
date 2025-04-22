					QuickAccessDialog.this.contents = new QuickAccessContents(providers) {
						@Override
						protected void updateFeedback(boolean filterTextEmpty,
								boolean showAllMatches) {
							if (filterTextEmpty) {
								setInfoText(QuickAccessMessages.QuickAccess_StartTypingToFindMatches);
							} else {
								TriggerSequence[] sequences = getInvokingCommandKeySequences();
								if (showAllMatches || sequences == null
										|| sequences.length == 0) {
								} else {
									setInfoText(NLS
											.bind(QuickAccessMessages.QuickAccess_PressKeyToShowAllMatches,
													sequences[0].format()));
								}
							}
						}

						@Override
						protected void doClose() {
							QuickAccessDialog.this.close();
						}

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

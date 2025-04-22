						TriggerSequence[] sequences = getInvokingCommandKeySequences();
						if (showAllMatches || sequences == null || sequences.length == 0) {
						} else {
							setInfoText(NLS.bind(QuickAccessMessages.QuickAccess_PressKeyToShowAllMatches,
									sequences[0].format()));
						}

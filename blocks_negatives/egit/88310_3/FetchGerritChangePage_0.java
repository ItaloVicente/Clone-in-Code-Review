									new Comparator<Change>() {
										@Override
										public int compare(Change o1, Change o2) {
											int changeDiff = o2.changeNumber
													.compareTo(o1.changeNumber);
											if (changeDiff == 0)
												changeDiff = o2
														.getPatchSetNumber()
														.compareTo(
																o1.getPatchSetNumber());
											return changeDiff;
										}
									});

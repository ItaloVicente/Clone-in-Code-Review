						MenuItem theItem = null;
						Control control = (Control) bot.widget;
						control.notifyListeners(SWT.MenuDetect, new Event());
						Menu menu = control.getMenu();
						for (String text : texts) {
							Matcher<?> matcher = allOf(
									instanceOf(MenuItem.class),
									withMnemonic(text));
							theItem = show(menu, matcher);
							if (theItem != null) {
								menu = theItem.getMenu();
							} else {
								hide(menu);
								break;
							}
						}

					String text = calcText(historyIndex, item);
					MenuItem mi = new MenuItem(menu, SWT.PUSH, menuIndex[0]);
					++menuIndex[0];
					mi.setText(text);
					mi.addSelectionListener(widgetSelectedAdapter(e -> open(item)));
				}

				@Override

				for (CTabItem tabItem : ((CTabFolder) widget).getItems()) {
					if (tabItem.isDisposed()) {
						break;
					} else {
						childCount++;
					}
				}

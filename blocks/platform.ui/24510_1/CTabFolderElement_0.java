				for (CTabItem tabItem : ((CTabFolder) widget).getItems()) {
					if (tabItem.isDisposed()) {
						System.err.println("CTabItem was disposed");
						break;
					} else {
						childCount++;
					}
				}

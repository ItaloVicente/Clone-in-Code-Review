				item.addDisposeListener(new DisposeListener() {
					@Override
					public void widgetDisposed(DisposeEvent e) {
						if (!treeIsDisposed) {
							Object data = item.getData();
							if (usingElementMap() && data != null) {
								unmapElement(data, item);
							}

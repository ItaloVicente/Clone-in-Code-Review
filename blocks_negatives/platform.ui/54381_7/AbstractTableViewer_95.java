
					Object element = resolveElement(index);
					if (element == null) {
						IContentProvider contentProvider = getContentProvider();
						if (contentProvider instanceof ILazyContentProvider) {
							((ILazyContentProvider) contentProvider)
									.updateElement(index);
							return;
						}
					}

					associate(element, item);
					updateItem(item, element);

				new ViewerComparator(CommonUtils.STRING_ASCENDING_COMPARATOR) {
					@Override
					public int category(Object element) {
						if (element instanceof DiffContentProvider.Folder) {
							return 0;
						} else {
							return 1;
						}
					}
				});

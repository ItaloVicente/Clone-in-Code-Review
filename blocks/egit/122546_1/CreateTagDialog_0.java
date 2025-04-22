		tagViewer.setComparator(new ViewerComparator() {
			@Override
			protected Comparator<? super String> getComparator() {
				return CommonUtils.STRING_ASCENDING_COMPARATOR;
			}
		});

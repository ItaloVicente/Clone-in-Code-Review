	private ViewerComparator getViewerComparator() {
		return new ViewerComparator(CommonUtils.STRING_ASCENDING_COMPARATOR) {
			@Override
			public int compare(Viewer viewer, Object e1, Object e2) {
				if(e1 instanceof Ref && e2 instanceof Ref) {
					return CommonUtils.REF_ASCENDING_COMPARATOR
							.compare((Ref) e1, (Ref) e2);
				}
				return super.compare(viewer, e1, e2);
			}
		};
	}


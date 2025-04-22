	public EStructuralFeatureIterator(
		EStructuralFeatureOwnerPointer pointer,
		String name,
		boolean reverse,
		NodePointer startWith) {
		propertyNodePointer =
			(EStructuralFeaturePointer) pointer.getPropertyPointer().clone();
		this.name = name;
		this.reverse = reverse;
		this.includeStart = true;
		if (reverse) {
			this.startPropertyIndex = EStructuralFeaturePointer.UNSPECIFIED_PROPERTY;
			this.startIndex = -1;
		}
		if (startWith != null) {
			while (startWith != null
					&& startWith.getImmediateParentPointer() != pointer) {
				startWith = startWith.getImmediateParentPointer();
			}
			if (startWith == null) {
				throw new JXPathException(
					"PropertyIerator startWith parameter is "
						+ "not a child of the supplied parent");
			}
			this.startPropertyIndex =
				((EStructuralFeaturePointer) startWith).getPropertyIndex();
			this.startIndex = startWith.getIndex();
			if (this.startIndex == NodePointer.WHOLE_COLLECTION) {
				this.startIndex = 0;
			}
			this.includeStart = false;
			if (reverse && startIndex == -1) {
				this.includeStart = true;
			}
		}
	}

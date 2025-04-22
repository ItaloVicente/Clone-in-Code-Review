	public String toString() {
		return "ModelBlob[objectId=" + change.getObjectId() + ", location=" + getLocation() + "]"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	}

	public Image getImage() {
		return null;
	}

	public ITypedElement getAncestor() {
		prepareTypedElements();
		return ancestorElement;
	}

	public ITypedElement getLeft() {
		prepareTypedElements();
		return leftElement;
	}


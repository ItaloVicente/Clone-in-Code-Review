	public void dispose() {
		this.commitImage.dispose();
		super.dispose();
	}

	/**
	 * @see org.eclipse.jface.viewers.LabelProvider#getImage(java.lang.Object)
	 */
	public Image getImage(Object element) {
		return this.commitImage;

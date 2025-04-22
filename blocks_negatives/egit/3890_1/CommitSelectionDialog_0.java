	private static class CommitLabelProvider extends LabelProvider implements
			IStyledLabelProvider {

		private Image commitImage = UIIcons.CHANGESET.createImage();

		public void dispose() {
			this.commitImage.dispose();
			super.dispose();
		}

		public Image getImage(Object element) {
			return this.commitImage;
		}

	/**
	 * A message line displaying a status.
	 */
	private static class MessageLine extends CLabel {

		/**
		 * Creates a new message line as a child of the given parent.
		 *
		 * @param parent
		 */
		public MessageLine(Composite parent) {
			this(parent, SWT.LEFT);
		}

		/**
		 * Creates a new message line as a child of the parent and with the
		 * given SWT stylebits.
		 *
		 * @param parent
		 * @param style
		 */
		public MessageLine(Composite parent, int style) {
			super(parent, style);
		}

		/**
		 * Find an image assocated with the status.
		 *
		 * @param status
		 * @return Image
		 */
		private Image findImage(IStatus status) {
			if (status.isOK()) {
				return null;
			} else if (status.matches(IStatus.ERROR)) {
				return JFaceResources.getImage(Dialog.DLG_IMG_MESSAGE_ERROR);
			} else if (status.matches(IStatus.WARNING)) {
				return JFaceResources.getImage(Dialog.DLG_IMG_MESSAGE_WARNING);
			} else if (status.matches(IStatus.INFO)) {
				return JFaceResources.getImage(Dialog.DLG_IMG_MESSAGE_INFO);
			}
			return null;
		}

		/**
		 * Sets the message and image to the given status.
		 *
		 * @param status
		 *            IStatus or <code>null</code>. <code>null</code> will
		 *            set the empty text and no image.
		 */
		public void setErrorStatus(IStatus status) {
			if (status != null && !status.isOK()) {
				String message = status.getMessage();
				if (message != null && message.length() > 0) {
					setText(LegacyActionTools.escapeMnemonics(message));
					MessageLine.this.setImage(findImage(status));
					return;
				}
			}
			MessageLine.this.setImage(null);
		}
	}


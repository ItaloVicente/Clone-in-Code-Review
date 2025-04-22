	public static class Builder {

		private int kind;
		private String title;
		private int style = SWT.NONE;
		private String message = null;
		private Image specificImage;
		private String[] buttonLabels = null;
		private int defaultButtonIndex = 0;

		public Builder(int kind, String title) {
			this.kind = kind;
			this.title = title;
		}

		public Builder sheet() {
			style = SWT.SHEET;
			return this;
		}

		public void open(Shell shell) {
			String[] buttonLabels = this.buttonLabels != null ? this.buttonLabels : getButtonLabels(kind);
			MessageDialog dialog = new MessageDialog(shell, this.title, this.specificImage, this.message, this.kind,
					defaultButtonIndex, buttonLabels);
			dialog.setShellStyle(dialog.getShellStyle() | this.style);
			if (this.specificImage != null) {
				dialog.image = this.specificImage;
			}
			dialog.open();
		}

		public Builder message(String message) {
			this.message = message;
			return this;
		}

		public Builder image(Image image) {
			this.specificImage = image;
			return this;
		}

		public Builder buttons(String... labels) {
			this.buttonLabels = labels;
			return this;
		}

		public Builder defaultButton(int defaultButtonIndex) {
			this.defaultButtonIndex = defaultButtonIndex;
			return this;
		}
	}

	public static Builder info(String title) {
		return new Builder(MessageDialog.INFORMATION, title);
	}

	public static Builder cancableQuestion(String title) {
		return new Builder(MessageDialog.QUESTION_WITH_CANCEL, title);
	}

	public static Builder confirm(String title) {
		return new Builder(MessageDialog.CONFIRM, title);
	}

	public static Builder question(String title) {
		return new Builder(MessageDialog.QUESTION, title);
	}

	public static Builder error(String title) {
		return new Builder(MessageDialog.ERROR, title);
	}

	public static Builder warning(String title) {
		return new Builder(MessageDialog.WARNING, title);
	}


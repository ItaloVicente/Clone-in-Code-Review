	public static class Content extends AbstractModelObject {
		private String text;

		public Content(String text) {
			this.text = text;
		}

		public String getText() {
			return text;
		}

		public void setText(String text) {
			firePropertyChange("text", this.text, this.text = text);
			this.text = text;
		}

	}


		@Override
		public String getName() {
			return UIText.DiffEditorPage_Title;
		}

		@Override
		public boolean equals(Object obj) {
			return super.equals(obj) && (obj instanceof DiffEditorInput)
					&& document.equals(((DiffEditorInput) obj).document);
		}

		@Override
		public int hashCode() {
			return super.hashCode() ^ document.hashCode();
		}

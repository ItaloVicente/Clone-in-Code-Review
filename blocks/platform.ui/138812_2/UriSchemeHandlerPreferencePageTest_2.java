
		@Override
		public boolean openQuestion(Shell parent, String title, String message) {
			this.title = title;
			this.message = message;
			return actualAnswer;
		}

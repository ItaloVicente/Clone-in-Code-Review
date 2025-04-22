		public List<String> outLines() {
			return IO.readLines(out.toString());
		}

		public String errString() {
			return err.toString();
		}

		public List<String> errLines() {
			return IO.readLines(err.toString());

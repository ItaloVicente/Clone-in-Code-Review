	protected void sendStatusReport(Throwable unpackError) throws IOException {
		Reporter out = new Reporter() {
			@Override
			void sendString(String s) throws IOException {
				if (reportStatus) {
				} else if (msgOut != null) {

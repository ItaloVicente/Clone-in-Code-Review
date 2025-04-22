			if (reportStatus) {
				sendStatusReport(true, unpackError, new Reporter() {
					@Override
					void sendString(String s) throws IOException {
					}
				});
				pckOut.end();
			} else if (msgOut != null) {
				sendStatusReport(false, unpackError, new Reporter() {
					@Override
					void sendString(String s) throws IOException {
					}
				});
			}

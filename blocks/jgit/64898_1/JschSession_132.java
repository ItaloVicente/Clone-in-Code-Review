		private void closeOutputStream() {
			if (outputStream != null) {
				try {
					outputStream.close();
				} catch (IOException ioe) {
				}
			}
		}


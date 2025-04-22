			outWriter.close();
		} catch (Exception e) {
			if (outWriter != null) {
				try {
					outWriter.close();
				} catch (IOException ioe) {
				}
			}

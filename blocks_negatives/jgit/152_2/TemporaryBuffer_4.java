		if (diskOut != null) {
			try {
				diskOut.close();
			} catch (IOException err) {
			} finally {
				diskOut = null;

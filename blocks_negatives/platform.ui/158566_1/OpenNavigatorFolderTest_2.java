		finally {
			try {
				if (zis != null) {
					zis.close();
				}
			}
			catch (IOException e) {
				fail(e.getMessage());
			}
		}

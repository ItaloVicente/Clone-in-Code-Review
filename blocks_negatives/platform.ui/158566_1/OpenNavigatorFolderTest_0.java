				finally {
					try {
						baos.close();
					}
					catch (IOException e) {
						fail(e.getMessage());
					}
				}

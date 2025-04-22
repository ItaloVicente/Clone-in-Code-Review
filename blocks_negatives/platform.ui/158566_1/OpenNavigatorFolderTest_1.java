				finally {
					try {
						bais.close();
					}
					catch (IOException e) {
						fail(e.getMessage());
					}
				}

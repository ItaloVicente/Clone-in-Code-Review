			for (int retry = 0; retry < 10; retry++) {
				try {
					FileUtils.delete(dir
				}
				catch (IOException ex) {
					if (!invokeGc) {
						throw ex;
					}

					System.gc();
				}
			}

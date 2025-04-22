			try {
				final FileInputStream inStream = new FileInputStream(liveFile);
				try {
					clear();
					readFrom(inStream);
				} finally {
					try {
						inStream.close();
					} catch (IOException err2) {
					}
				}

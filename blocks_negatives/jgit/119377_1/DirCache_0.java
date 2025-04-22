			try {
				final FileInputStream inStream = new FileInputStream(liveFile);
				try {
					clear();
					readFrom(inStream);
				} finally {

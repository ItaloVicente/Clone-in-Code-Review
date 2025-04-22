		try {
			byte[] truncatedData = new byte[readFully.length - 1];
			System.arraycopy(readFully, 0, truncatedData, 0,
					truncatedData.length);
			fileOutputStream.write(truncatedData);
		} finally {
			fileOutputStream.close();
		}

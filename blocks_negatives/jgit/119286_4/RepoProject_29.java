			FileInputStream input = new FileInputStream(srcFile);
			try {
				FileOutputStream output = new FileOutputStream(destFile);
				try {
					FileChannel channel = input.getChannel();
					output.getChannel().transferFrom(
							channel, 0, channel.size());
				} finally {
					output.close();
				}
			} finally {
				input.close();

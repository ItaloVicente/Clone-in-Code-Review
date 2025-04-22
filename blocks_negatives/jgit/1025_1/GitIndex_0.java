		FileChannel channel = new FileOutputStream(file).getChannel();
		ByteBuffer buffer = ByteBuffer.wrap(bytes);
		int j = channel.write(buffer);
		if (j != bytes.length)
			throw new IOException(MessageFormat.format(JGitText.get().couldNotWriteFile, file));
		channel.close();

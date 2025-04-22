
		final OutputStream out;
		if (fsync)
			out = new ChannelOutputStream(os.getChannel());
		else
			out = os;


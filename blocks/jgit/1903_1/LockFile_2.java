
		final OutputStream out;
		if (fsync)
			out = Channels.newOutputStream(os.getChannel());
		else
			out = os;


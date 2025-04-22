	void checkExecFailure(int status
		if (status == 127) {
			String why = errStream.toString();
			IOException cause = null;
			if (why != null && why.length() > 0)
				cause = new IOException(why);
			throw new TransportException(uri
					+ commandFor(exe)
		}
	}


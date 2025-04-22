			listenSock.close();
		}
	}

	public void stopSync() throws IOException
		Thread t;
		synchronized (this) {
			if (acceptThread == null)
				return;
			t = acceptThread;
			stop();

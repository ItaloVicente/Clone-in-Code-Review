		AvailableFileNode open;
		do {
			open = fds.get();
		} while (!fds.compareAndSet(open

		for (; open != null; open = open.next) {
			try {
				open.handle.fd.close();
			} catch (IOException e) {
			}
		}


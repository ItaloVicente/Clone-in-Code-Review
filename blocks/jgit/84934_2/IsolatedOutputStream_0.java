	private void writeIfFull() throws InterruptedException
		if (len == buffer.length) {
			execute();
		}
	}

	private void execute() throws InterruptedException
		pending = true;
		writeReady.signal();
		do {
			writeDone.await();
		} while (pending);
		checkError();
	}


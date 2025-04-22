
			final MessageWriter msg = new MessageWriter();
			setMessageWriter(msg);

			receivePack = spawn(getOptionReceivePack());

			final InputStream rpErr = receivePack.getErrorStream();
			errorReaderThread = new StreamCopyThread(rpErr
			errorReaderThread.start();


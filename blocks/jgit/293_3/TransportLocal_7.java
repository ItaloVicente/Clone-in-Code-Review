
			final MessageWriter msg = new MessageWriter();
			setMessageWriter(msg);

			uploadPack = spawn(getOptionUploadPack());

			final InputStream upErr = uploadPack.getErrorStream();
			errorReaderThread = new StreamCopyThread(upErr
			errorReaderThread.start();


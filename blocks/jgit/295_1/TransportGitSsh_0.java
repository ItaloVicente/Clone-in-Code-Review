				channel = exec(getOptionUploadPack());
				if (!channel.isConnected())
					throw new TransportException(uri

				final InputStream upErr = channel.getErrStream();
				errorThread = new StreamCopyThread(upErr
				errorThread.start();

				init(channel.getInputStream()

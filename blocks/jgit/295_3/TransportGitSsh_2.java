				channel = exec(getOptionReceivePack());
				if (!channel.isConnected())
					throw new TransportException(uri

				final InputStream rpErr = channel.getErrStream();
				errorThread = new StreamCopyThread(rpErr
				errorThread.start();

				init(channel.getInputStream()

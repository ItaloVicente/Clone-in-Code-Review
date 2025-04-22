			Thread outConsumer = new StreamConsumer(process.getInputStream());
			outConsumer.setName("External browser output reader"); //$NON-NLS-1$
			outConsumer.start();
			Thread errConsumer = new StreamConsumer(process.getErrorStream());
			errConsumer.setName("External browser  error reader"); //$NON-NLS-1$
			errConsumer.start();

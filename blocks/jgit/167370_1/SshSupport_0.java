					provider
			timeoutMillis -= TimeUnit.NANOSECONDS
					.toMillis(System.nanoTime() - start);
			int commandTimeout = (int) TimeUnit.MILLISECONDS
					.toSeconds(timeoutMillis);
			if (commandTimeout <= 0) {
				commandTimeout = 1;
			}
			process = session.exec(command

					provider
			int commandTimeout = timeout;
			if (timeout > 0) {
				elapsed = System.nanoTime() - elapsed;
				commandTimeout -= (int) TimeUnit.NANOSECONDS.toSeconds(elapsed);
				if (commandTimeout <= 0) {
					commandTimeout = 1;
				}
			}
			process = session.exec(command

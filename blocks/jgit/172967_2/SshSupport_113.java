				boolean finished = false;
				if (timeout <= 0) {
					process.waitFor();
					finished = true;
				} else {
					finished = process.waitFor(commandTimeout
							TimeUnit.SECONDS);
				}
				if (finished) {

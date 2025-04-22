				boolean exception = true;
				try {
					display.asyncExec(timerExec); // may throw SwtException
					exception = false;
				} finally {
					if (exception) {
						scheduled.set(false);
					}
				}

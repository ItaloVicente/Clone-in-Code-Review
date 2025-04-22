				try {
					display.asyncExec(timerExec); // may throw SwtException
					schedule = false;
				} finally {
					if (!schedule) {
						scheduled.set(false);
					}
				}

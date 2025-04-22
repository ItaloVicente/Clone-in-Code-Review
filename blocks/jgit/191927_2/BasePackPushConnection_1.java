			try {
				writeCommands(refUpdates.values()

				if (pushOptions != null && capablePushOptions)
					transmitOptions();
				if (writePack)
					writePack(refUpdates
				if (sentCommand) {
					if (capableReport)
						readStatusReport(refUpdates);
					if (capableSideBand) {
						int b = in.read();
						if (0 <= b) {
							throw new TransportException(uri
									MessageFormat.format(
											JGitText.get().expectedEOFReceived
											Character.valueOf((char) b)));
						}
					}
				}
			} finally {
				if (in instanceof SideBandInputStream) {
					((SideBandInputStream) in).drainMessages();

			writeCommands(refUpdates.values(), monitor, outputStream);

			if (pushOptions != null && capablePushOptions)
				transmitOptions();
			if (writePack)
				writePack(refUpdates, monitor);
			if (sentCommand) {
				if (capableReport)
					readStatusReport(refUpdates);
				if (capableSideBand) {
					int b = in.read();
					if (0 <= b)
						throw new TransportException(uri, MessageFormat.format(
								JGitText.get().expectedEOFReceived,
								Character.valueOf((char) b)));

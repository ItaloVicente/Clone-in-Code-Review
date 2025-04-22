			if (sentCommand) {
				if (capableReport)
					readStatusReport(refUpdates);
				if (capableSideBand) {
					int b = in.read();
					if (0 <= b)
						throw new TransportException(uri
								+ " received '" + (char) b + "' instead");
				}
			}

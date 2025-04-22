			if (sendPack && !biDirectionalPipe) {
				int eof = rawIn.read();
				if (0 <= eof) {
					sendPack = false;
					throw new CorruptObjectException(MessageFormat.format(
							JGitText.get().expectedEOFReceived
				}
			}

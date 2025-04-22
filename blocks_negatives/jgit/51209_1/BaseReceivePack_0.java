		for (;;) {
			String rawLine;
			try {
				rawLine = pckIn.readStringRaw();
			} catch (EOFException eof) {
				if (commands.isEmpty())
					return;
				throw eof;
			}
			if (rawLine == PacketLineIn.END) {
				break;
			}
			String line = chomp(rawLine);

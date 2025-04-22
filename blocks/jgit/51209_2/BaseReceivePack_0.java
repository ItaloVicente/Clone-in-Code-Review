		try {
			for (;;) {
				String line;
				try {
					rawLine = pckIn.readString();
				} catch (EOFException eof) {
					if (commands.isEmpty())
						return;
					throw eof;
				}
				if (rawLine == PacketLineIn.END) {
					break;
				}

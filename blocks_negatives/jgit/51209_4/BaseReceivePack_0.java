		for (;;) {
			String line;
			try {
				line = pckIn.readString();
			} catch (EOFException eof) {
				if (commands.isEmpty())
					return;
				throw eof;
			}
			if (line == PacketLineIn.END) {
				break;
			}

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

				clientShallowCommits.add(ObjectId.fromString(line.substring(8, 48)));
				continue;
			}

			if (firstLine == null) {
				firstLine = new FirstLine(line);
				enabledCapabilities = firstLine.getCapabilities();
				line = firstLine.getLine();

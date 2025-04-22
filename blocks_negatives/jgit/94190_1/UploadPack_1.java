			String line;
			try {
				line = pckIn.readString();
			} catch (EOFException eof) {
				if (!biDirectionalPipe && depth > 0)
					return false;
				throw eof;
			}

	private void recvWants() throws IOException {
		boolean isFirst = true;
		for (;;) {
			String line;
			try {
				line = pckIn.readString();
			} catch (EOFException eof) {
				if (isFirst)
					break;
				throw eof;
			}

			if (line == PacketLineIn.END)
				break;

				depth = Integer.parseInt(line.substring(7));
				if (depth <= 0) {
					throw new PackProtocolException(
							MessageFormat.format(JGitText.get().invalidDepth,
									Integer.valueOf(depth)));
				}
				continue;
			}

				clientShallowCommits.add(ObjectId.fromString(line.substring(8)));
				continue;
			}

				throw new PackProtocolException(MessageFormat.format(JGitText.get().expectedGot, "want", line)); //$NON-NLS-1$

			if (isFirst) {
				if (line.length() > 45) {
					FirstLine firstLine = new FirstLine(line);
					options = firstLine.getOptions();
					line = firstLine.getLine();
				} else
					options = Collections.emptySet();
			}

			wantIds.add(ObjectId.fromString(line.substring(5)));
			isFirst = false;
		}
	}


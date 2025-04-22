	private void recvWants() throws IOException {
		boolean isFirst = true;
		boolean filterReceived = false;
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

			if (transferConfig.isAllowFilter()
				String arg = line.substring(OPTION_FILTER.length() + 1);

				if (filterReceived) {
					throw new PackProtocolException(JGitText.get().tooManyFilters);
				}
				filterReceived = true;

				filterBlobLimit = ProtocolV2Parser.filterLine(arg);
				continue;
			}

				throw new PackProtocolException(MessageFormat.format(JGitText.get().expectedGot, "want", line)); //$NON-NLS-1$

			if (isFirst) {
				if (line.length() > 45) {
					FirstWant firstLine = FirstWant
							.fromLine(line);
					options = firstLine.getCapabilities();
					line = firstLine.getLine();
				} else
					options = Collections.emptySet();
			}

			wantIds.add(ObjectId.fromString(line.substring(5)));
			isFirst = false;
		}
	}


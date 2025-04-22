				boolean adjustForSpace = wordIndex != 0;

				if (wordIndex == 0 && lastChunkWasWrapped) {
					if (wordLength != 0 && !STARTS_WITH_SYMBOL.matcher(word).matches()) {
						wrapEdits.add(new WrapEdit(offset - lineDelimiterLength, lineDelimiterLength, " ")); //$NON-NLS-1$
						offset -= lineDelimiterLength;
						adjustForSpace = true;
						lineLength = offset - lastChunkWrappedOffset;
					}
					lastChunkWasWrapped = false;
				}

				int newLineLength = lineLength + spaceLength + wordLength;


				if (wordIndex == 0 && lastChunkWasWrapped) {
					if (wordLength != 0 && STARTS_WITH_WORD.matcher(word).matches()) {
						wrapEdits.add(new WrapEdit(offset - lineDelimiterLength, lineDelimiterLength, " ")); //$NON-NLS-1$
						lineLength += lastChunkWrappedWordLength + spaceLength;
						offset += (spaceLength - lineDelimiterLength);
					}
					lastChunkWasWrapped = false;
				}

				int newLineLength = lineLength + wordLength + spaceLength;

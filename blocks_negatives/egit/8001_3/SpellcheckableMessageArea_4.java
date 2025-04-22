
				int newLineLength = lineLength + spaceLength + wordLength;
				if (newLineLength > maxLineLength) {
					/* don't break before a single long word */
					if (lineLength != 0) {
						wrapEdits.add(new WrapEdit(offset, spaceLength, lineDelimiter));
						/* adjust for the shifting of text after the edit is applied */
						offset += lineDelimiterLength;
						adjustForSpace = false;
						lastChunkWasWrapped = true;
						lastChunkWrappedOffset = offset;

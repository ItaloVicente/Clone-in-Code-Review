		if (maxLines > 0 && linesWritten > maxLines) {
			if (linesWritten == maxLines + 1) {
				int start = stream.offset;
				stream.flushLine();
				stream.write(
						NLS.bind(UIText.DiffStyleRangeFormatter_diffTruncated,
								Integer.valueOf(maxLines)));
				stream.write("\n"); //$NON-NLS-1$
				addRange(Type.HEADLINE, start, stream.offset);
				linesWritten++;
			}
			return;
		}

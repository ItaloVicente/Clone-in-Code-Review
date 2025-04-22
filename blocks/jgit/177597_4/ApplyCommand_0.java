			if (!hunkLine.hasRemaining()) {
				if (pos >= limit || newLines.get(pos).hasRemaining()) {
					return false;
				}
				pos++;
				continue;
			}

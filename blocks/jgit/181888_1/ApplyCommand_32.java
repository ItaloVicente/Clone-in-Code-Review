			ByteBuffer hunkLine = hunkLines.get(j);
			if (!hunkLine.hasRemaining()) {
				if (pos >= limit || newLines.get(pos).hasRemaining()) {
					return false;
				}
				pos++;
				continue;
			}
			switch (hunkLine.array()[hunkLine.position()]) {

				ByteBuffer hunkLine = hunkLines.get(j);
				if (!hunkLine.hasRemaining()) {
					applyAt++;
					continue;
				}
				switch (hunkLine.array()[hunkLine.position()]) {

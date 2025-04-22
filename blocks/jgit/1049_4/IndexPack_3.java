			int off = 0;
			long cnt = 0;
			int p = fill(src
			inf.setInput(buf

			do {
				int r = inf.inflate(dst
				if (r == 0) {
					if (inf.finished())
						break;
					if (inf.needsInput()) {
						if (p >= 0) {
							crc.update(buf
							use(bAvail);
						}
						p = fill(src
						inf.setInput(buf
					} else {
						throw new CorruptObjectException(MessageFormat.format(
								JGitText.get().packfileCorruptionDetected
								JGitText.get().unknownZlibError));

			int p = fillFromFile(24);
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
						p = fillFromFile(24);
						inf.setInput(buf
					} else
						throw new DataFormatException();

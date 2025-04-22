			long n = 0;
			int p = fillFromInput(24);
			inf.setInput(buf
			do {
				int r = inf.inflate(objectData
				if (r == 0) {
					if (inf.finished())
						break;
					if (inf.needsInput()) {
						if (p >= 0) {
							crc.update(buf
							use(bAvail);
						}
						p = fillFromInput(24);
						inf.setInput(buf
					} else
						throw new DataFormatException();

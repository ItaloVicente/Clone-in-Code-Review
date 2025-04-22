			final byte[] dst = objectData;
			int n = 0;
			int p = -1;
			while (!inf.finished()) {
				if (inf.needsInput()) {
					if (p >= 0) {
						crc.update(buf, p, bAvail);
						use(bAvail);
					}
					p = fillFromInput(1);
					inf.setInput(buf, p, bAvail);
				}

				int free = dst.length - n;
				if (free < 8) {
					sz -= n;
					n = 0;
					free = dst.length;

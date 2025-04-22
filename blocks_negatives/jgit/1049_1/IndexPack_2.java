			int p = -1;
			while (!inf.finished()) {
				if (inf.needsInput()) {
					if (p >= 0) {
						crc.update(buf, p, bAvail);
						use(bAvail);
					}
					p = fillFromFile(1);
					inf.setInput(buf, p, bAvail);

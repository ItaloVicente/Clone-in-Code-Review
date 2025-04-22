		while (s.remaining() > 0 && !inf.finished()) {
			if (inf.needsInput()) {
				final int n = Math.min(s.remaining(), tmp.length);
				s.get(tmp, 0, n);
				inf.setInput(tmp, 0, n);
			}
			inf.inflate(verifyGarbageBuffer, 0, verifyGarbageBuffer.length);
		}
		while (!inf.finished() && !inf.needsInput())
			inf.inflate(verifyGarbageBuffer, 0, verifyGarbageBuffer.length);

		while (!inf.finished()) {
			if (inf.needsInput()) {
				inf.setInput(array, pos, array.length - pos);
				break;
			}
			inf.inflate(verifyGarbageBuffer, 0, verifyGarbageBuffer.length);
		}
		while (!inf.finished() && !inf.needsInput())
			inf.inflate(verifyGarbageBuffer, 0, verifyGarbageBuffer.length);

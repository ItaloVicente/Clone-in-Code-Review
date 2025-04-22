		if (oe.getCRC() != (int) crc.getValue()) {
			throw new IOException(MessageFormat.format(
					JGitText.get().corruptionDetectedReReadingAt
					oe.getOffset()));
		}

		resolveDeltas(nextVisit(visit)
	}

	private void resolveDeltas(DeltaVisit visit
			throws IOException {
		do {
			final long pos = visit.delta.position;
			crc.reset();
			position(pos);
			int c = readFrom(Source.FILE);
			final int typeCode = (c >> 4) & 7;
			long sz = c & 15;
			int shift = 4;
			while ((c & 0x80) != 0) {
				c = readFrom(Source.FILE);
				sz += (c & 0x7f) << shift;
				shift += 7;
			}

			switch (typeCode) {
			case Constants.OBJ_OFS_DELTA: {
				c = readFrom(Source.FILE) & 0xff;
				while ((c & 128) != 0)
					c = readFrom(Source.FILE) & 0xff;
				visit.data = BinaryDelta.apply(visit.parent.data
				break;
			}
			case Constants.OBJ_REF_DELTA: {
				crc.update(buf
				use(20);
				visit.data = BinaryDelta.apply(visit.parent.data
				break;
			}
			default:
				throw new IOException(MessageFormat.format(JGitText.get().unknownObjectType
			}

			final int crc32 = (int) crc.getValue();
			if (visit.delta.crc != crc32)
				throw new IOException(MessageFormat.format(JGitText.get().corruptionDetectedReReadingAt


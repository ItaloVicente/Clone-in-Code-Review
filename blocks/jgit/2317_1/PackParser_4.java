			if (!checkCRC(oe.getCRC())) {
				throw new IOException(MessageFormat.format(
						JGitText.get().corruptionDetectedReReadingAt
						oe.getOffset()));
			}
		} else {
			ObjectLoader ldr = reader().open(oe);
			info = new ObjectTypeAndSize();
			info.type = ldr.getType();
			visit.data = ldr.getCachedBytes(Integer.MAX_VALUE);
			visit.id = oe;

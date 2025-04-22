			try {
				curs.inflateVerify(this, dataOffset);
			} catch (DataFormatException dfe) {
				final CorruptObjectException coe;
				coe = new CorruptObjectException(MessageFormat.format(
						JGitText.get().objectAtHasBadZlibStream, objectOffset, getPackFile()));
				coe.initCause(dfe);
				throw coe;

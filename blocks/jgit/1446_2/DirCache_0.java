		extended = false;
		if (ver == 3) {
			if (!readOnly) {
				throw new CorruptObjectException(
						"DIRC version 3 is only supported in read-only mode.");
			}

			extended = true;
		} else if (ver != 2)
			throw new CorruptObjectException(MessageFormat.format(
					JGitText.get().unknownDIRCVersion

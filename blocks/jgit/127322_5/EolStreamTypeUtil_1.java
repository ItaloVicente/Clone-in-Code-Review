	private static EolStreamType getOutputFormat(WorkingTreeOptions options) {
		switch (options.getAutoCRLF()) {
		case TRUE:
			return EolStreamType.TEXT_CRLF;
		default:
		}
		switch (options.getEOL()) {
		case CRLF:
			return EolStreamType.TEXT_CRLF;
		case NATIVE:
			if (SystemReader.getInstance().isWindows()) {
				return EolStreamType.TEXT_CRLF;
			}
			return EolStreamType.TEXT_LF;
		case LF:
		default:
			break;
		}
		return EolStreamType.DIRECT;
	}


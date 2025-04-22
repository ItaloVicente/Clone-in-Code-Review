			return EolStreamType.TEXT_CRLF;
			return FORCE_EOL_LF_ON_CHECKOUT ? EolStreamType.TEXT_LF
					: EolStreamType.DIRECT;

			switch (options.getAutoCRLF()) {
			case TRUE:
				return EolStreamType.TEXT_CRLF;
			default:
			}
			switch (options.getEOL()) {
			case CRLF:

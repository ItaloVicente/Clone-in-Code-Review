			}
			switch (options.getEOL()) {
			case CRLF:
				return EolStreamType.AUTO_CRLF;
			case LF:
				return FORCE_EOL_LF_ON_CHECKOUT ? EolStreamType.TEXT_LF
						: EolStreamType.DIRECT;
			case NATIVE:
			default:
				return EolStreamType.DIRECT;

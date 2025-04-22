			case (byte) '\r':
			case (byte) '\n':
			case (byte) '\t':
			case (byte) ' ':
				assertTrue(isWhitespace(c));
				break;
			default:
				assertFalse(isWhitespace(c));

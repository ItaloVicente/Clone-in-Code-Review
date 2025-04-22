		case '+':
			return 62;
		case '/':
			return 63;
		default:
			throw new IllegalArgumentException("Invalid char to decode: " + data); //$NON-NLS-1$

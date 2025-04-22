			switch (c) {
			case '<':
				return "lt"; //$NON-NLS-1$
			case '>':
				return "gt"; //$NON-NLS-1$
			case '"':
				return "quot"; //$NON-NLS-1$
			case '\'':
				return "apos"; //$NON-NLS-1$
			case '&':
				return "amp"; //$NON-NLS-1$
			case '\r':
				return "#x0D"; //$NON-NLS-1$
			case '\n':
				return "#x0A"; //$NON-NLS-1$
			case '\u0009':
				return "#x09"; //$NON-NLS-1$
			}
			return null;
		}
	}

			int colonIndex = id.indexOf(':');
			if (colonIndex >= 0) {
				secondaryId = true;
				descId = id.substring(0, colonIndex);
				descId += ":*"; //$NON-NLS-1$
			}

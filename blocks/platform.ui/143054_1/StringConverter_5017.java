			String s = value.substring(faceIndex + 1, heightIndex);
			if (BOLD_ITALIC.equals(s)) {
				style = SWT.BOLD | SWT.ITALIC;
			} else if (BOLD.equals(s)) {
				style = SWT.BOLD;
			} else if (ITALIC.equals(s)) {
				style = SWT.ITALIC;
			} else if (REGULAR.equals(s)) {
				style = SWT.NORMAL;
			} else {
				throw new DataFormatException("Unknown face name \"" + s + "\""); //$NON-NLS-2$//$NON-NLS-1$
			}
			name = value.substring(0, faceIndex);
		} catch (NoSuchElementException e) {
			throw new DataFormatException(e.getMessage());
		}
		return new FontData(name, height, style);
	}

		if (newContent != null) {
			modifiedContent = new byte[newContent.length];
			System.arraycopy(newContent, 0, modifiedContent, 0,
					newContent.length);
		} else
			modifiedContent = null;

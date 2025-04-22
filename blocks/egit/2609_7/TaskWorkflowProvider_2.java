
	private String normalizeBranchName(String name) {
		String normalized = name.replaceAll("\\s+", "_").replaceAll("\\W", ""); //$NON-NLS-1$//$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
		if (normalized.length() > 30)
			normalized = normalized.substring(0, 30);
		return normalized;
	}


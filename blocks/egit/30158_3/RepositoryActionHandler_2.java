		if (warn) {
			Shell shell = getShell(event);
			return SelectionUtils.getRepositoryOrWarn(selection, shell);
		} else {
			return SelectionUtils.getRepository(selection);
		}

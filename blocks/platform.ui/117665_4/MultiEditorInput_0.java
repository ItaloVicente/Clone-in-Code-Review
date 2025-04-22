		StringBuilder name = new StringBuilder();
		for (int i = 0; i < (input.length - 1); i++) {
			name.append(input[i].getName()).append("/"); //$NON-NLS-1$
		}
		name.append(input[input.length - 1].getName());
		return name.toString();
	}

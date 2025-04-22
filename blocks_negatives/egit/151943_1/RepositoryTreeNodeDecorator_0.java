		if (branches.size() == 1) {
			decorate = true;
			StringBuilder suffix = new StringBuilder();
			suffix.append(OPEN_BRACKET).append(branches.iterator().next())
					.append(']');
			decoration.addSuffix(suffix.toString());

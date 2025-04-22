		for (int i = 0; i < newLines.size(); i++) {
			String l = newLines.get(i);
			sb.append(l);
			if (i != newLines.size() - 1) {
				sb.append(System.lineSeparator());
			}

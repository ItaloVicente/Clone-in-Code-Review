		sb.setLength(0);
		sb.append("# This is a combination of ").append(count)
				.append(" commits.\n");
		sb.append(currSquashMessage
				.substring(currSquashMessage.indexOf('\n') + 1));
		sb.append("\n");
		if (isSquash) {
			sb.append("# This is the ").append(count).append(ordinal)
					.append(" commit message:\n");
			sb.append(commitToPick.getFullMessage());

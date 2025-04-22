		char commentChar = currSquashMessage.charAt(0);
		String newMessage = commitToPick.getFullMessage();
		if (!isSquash) {
			sb.append(commentChar).append(" This is a combination of ")
					.append(count).append(" commits.\n");
			sb.append(currSquashMessage
					.substring(currSquashMessage.indexOf('\n') + 1));
			sb.append('\n');
			sb.append(commentChar).append(" The ").append(count).append(ordinal)
					.append(" commit message will be skipped:\n")
					.append(commentChar).append(' ');
			sb.append(newMessage.replaceAll("([\n\r])"
					"$1" + commentChar + ' '));

			String currentMessage = currSquashMessage;
			if (commitConfig.isAutoCommentChar()) {
				String cleaned = CommitConfig.cleanText(currentMessage
						CommitConfig.CleanupMode.STRIP
						+ newMessage;
				char newCommentChar = commitConfig.getCommentChar(cleaned);
				if (newCommentChar != commentChar) {
					currentMessage = replaceCommentChar(currentMessage
							commentChar
					commentChar = newCommentChar;
				}
			}
			sb.append(commentChar).append(" This is a combination of ")
					.append(count).append(" commits.\n");
			sb.append(
					currentMessage.substring(currentMessage.indexOf('\n') + 1));
			sb.append('\n');
			sb.append(commentChar).append(" This is the ").append(count)
					.append(ordinal).append(" commit message:\n");
			sb.append(newMessage);

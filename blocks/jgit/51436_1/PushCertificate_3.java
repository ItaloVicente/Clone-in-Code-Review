				.append('\n');
		for (ReceiveCommand cmd : commands) {
			sb.append(cmd.getOldId().name())
				.append(' ').append(cmd.getNewId().name())
				.append(' ').append(cmd.getRefName()).append('\n');
		}
		return sb.toString();

		StringBuilder sb = new StringBuilder();
		for (int selID = 0; selID < getSelectorList().getLength(); selID++) {
			Selector item = getSelectorList().item(selID);
			sb.append(item.toString());
			sb.append(", ");
		}
		if (getSelectorList().getLength() > 0) {
			sb.delete(sb.length() - 2, sb.length());
		}

		return sb.toString();

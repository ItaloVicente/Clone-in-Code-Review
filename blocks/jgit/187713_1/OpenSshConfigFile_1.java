			String keyword = parts[0].strip();
			if (keyword.isEmpty()) {
				continue;
			}
			switch (keyword.charAt(0)) {
			case '#':
				continue;
			case '"':
				List<String> dequoted = parseList(keyword);
				break;
			default:
				int i = keyword.indexOf('#');
				if (i >= 0) {
					keyword = keyword.substring(0
				}
				break;
			}
			if (keyword.isEmpty()) {
				continue;
			}

		result.append("\n absolutePath: ");
		result.append(hasAbsolutePath());
		result.append("\n     segments: ");
		if (segments.length == 0) {
			result.append("<empty>");
		}
		for (int i = 0, len = segments.length; i < len; i++)
		{
			if (i > 0) {
			result.append("\n               ");
		}
			result.append(segments[i]);
		}
		result.append("\n        query: ");
		result.append(query);
		result.append("\n     fragment: ");
		result.append(fragment);
		return result.toString();

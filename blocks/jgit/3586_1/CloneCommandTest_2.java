	public static String allRefNames(List<Ref> refs) {
		StringBuilder sb = new StringBuilder();
		for (Ref f : refs) {
			if (sb.length() > 0)
				sb.append("
			sb.append(f.getName());
		}
		return sb.toString();
	}


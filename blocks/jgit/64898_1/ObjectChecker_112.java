	public enum ErrorType {


		public String getMessageId() {
			String n = name();
			StringBuilder r = new StringBuilder(n.length());
			for (int i = 0; i < n.length(); i++) {
				char c = n.charAt(i);
				if (c != '_') {
					r.append(StringUtils.toLowerCase(c));
				} else {
					r.append(n.charAt(++i));
				}
			}
			return r.toString();
		}
	}

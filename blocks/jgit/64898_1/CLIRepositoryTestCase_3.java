			s = s.trim();
			if (s != null && !s.isEmpty()) {
				b.append(s);
				b.append('\n');
			}
		}
		if (b.length() > 0 && b.charAt(b.length() - 1) == '\n') {
			b.deleteCharAt(b.length() - 1);

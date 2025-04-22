		while (items.hasNext()) {
			Object obj = items.next();
			if (obj instanceof FileDiff) {
				if (r.length() > 0)
					r.append(LINESEP);
				r.append(((FileDiff) obj).getPath());
			}

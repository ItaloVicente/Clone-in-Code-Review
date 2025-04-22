		if (!ranges.isEmpty()) {
			DiffStyleRange last = ranges.get(ranges.size() - 1);
			int lastEnd = last.start + last.length;
			if (last.diffType == Type.HEADLINE && lastEnd < start) {
				addRange(Type.HEADER, lastEnd, start);
			}
		}

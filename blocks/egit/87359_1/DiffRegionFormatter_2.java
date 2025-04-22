		if (regions.size() > 0) {
			DiffRegion last = regions.get(regions.size() - 1);
			if (last.diffType.equals(type)
					&& start == last.getOffset() + last.getLength()) {
				regions.remove(regions.size() - 1);
				start = last.getOffset();
			}
		}

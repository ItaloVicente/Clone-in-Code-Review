		public <S extends Sequence, C extends SequenceComparator<? super S>> EditList diff(
				C cmp, S a, S b) {
			Edit region = new Edit(0, a.size(), 0, b.size());
			region = cmp.reduceCommonStartEnd(a, b, region);

			switch (region.getType()) {
			case INSERT:
			case DELETE: {
				EditList r = new EditList();
				r.add(region);
				return r;
			}

			case REPLACE:
				return new MyersDiff<S>(cmp, a, b, region).getEdits();

			case EMPTY:
				return new EditList();

			default:
				throw new IllegalStateException();
			}

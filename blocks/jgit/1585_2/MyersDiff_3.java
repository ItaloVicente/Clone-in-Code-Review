			Edit region = new Edit(0
			region = cmp.reduceCommonStartEnd(a

			switch (region.getType()) {
			case INSERT:
			case DELETE: {
				EditList r = new EditList();
				r.add(region);
				return r;
			}

			case REPLACE:
				return new MyersDiff<S>(cmp

			case EMPTY:
				return new EditList();

			default:
				throw new IllegalStateException();
			}

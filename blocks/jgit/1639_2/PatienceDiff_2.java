
		private void diff(Edit r
			switch (r.getType()) {
			case INSERT:
			case DELETE:
				edits.add(r);
				break;

			case REPLACE:
				diffReplace(r
				break;

			case EMPTY:
			default:
				throw new IllegalStateException();
			}
		}

		private SubsequenceComparator<HashedSequence<S>> subcmp() {
			return new SubsequenceComparator<HashedSequence<S>>(cmp);
		}

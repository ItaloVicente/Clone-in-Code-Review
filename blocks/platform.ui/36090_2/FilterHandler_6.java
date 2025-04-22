		}

		@Override
		public CharSequence subSequence(int start, int end) {
			int lastOffset = first.length() + 1; // Offset of the last name in the sequence.
			if (end < lastOffset) {
				return first.subSequence(start, end);
			} else if (start < lastOffset) {
				return new CompoundName(first.substring(start), last.substring(0, end - lastOffset));
			} else {
				return last.subSequence(start - lastOffset, end - lastOffset);
			}
		}

		@Override
		public String toString() {
			return first + '.' + last;
		}
	}

	private final StackFrame[] filterFrames;
	private final Pattern[] filterPatterns;

	public FilterHandler(String commaSeparatedMethods) {
		String[] filters = commaSeparatedMethods.split(","); //$NON-NLS-1$

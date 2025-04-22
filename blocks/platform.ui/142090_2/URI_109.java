		for (int i = 0, len = segments.length; i < len; i++)
		{
			String segment = segments[i];
			if ((i < len - 1 && SEGMENT_EMPTY.equals(segment)) ||
					SEGMENT_SELF.equals(segment) ||
					SEGMENT_PARENT.equals(segment) && (
						!preserveRootParents || (
							i != 0 && !SEGMENT_PARENT.equals(segments[i - 1]))))
			{
				return true;
			}
		}
		return false;
	}

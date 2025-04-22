	protected int getExtendedFlags() {
		return (flags >>> EXT_SHIFT) & EXT_MASK;
	}

	protected boolean isExtendedFlag(int flag) {
		return (flags & (flag << EXT_SHIFT)) != 0;
	}

	protected void setExtendedFlag(int flag) {
		flags |= (flag & EXT_MASK) << EXT_SHIFT;
	}

	protected void clearExtendedFlag(int flag) {
		flags &= ~((flag & EXT_MASK) << EXT_SHIFT);
	}

	protected void setExtendedFlags(int extFlags) {
		flags = ((extFlags & EXT_MASK) << EXT_SHIFT) | (flags & NON_EXT_MASK);
	}


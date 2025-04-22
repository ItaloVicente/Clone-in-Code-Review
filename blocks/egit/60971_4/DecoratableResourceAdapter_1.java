		return "DecoratableResourceAdapter[" + getName() //$NON-NLS-1$
				+ (isTracked() ? ", tracked" : "") //$NON-NLS-1$ //$NON-NLS-2$
				+ (isIgnored() ? ", ignored" : "") //$NON-NLS-1$ //$NON-NLS-2$
				+ (isDirty() ? ", dirty" : "") //$NON-NLS-1$//$NON-NLS-2$
				+ (hasConflicts() ? ",conflicts" : "")//$NON-NLS-1$//$NON-NLS-2$
				+ ", staged=" + staged() //$NON-NLS-1$
				+ "]"; //$NON-NLS-1$

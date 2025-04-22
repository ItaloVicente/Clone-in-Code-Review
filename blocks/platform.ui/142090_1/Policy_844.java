	static {
		if (getDebugOption("/debug")) { //$NON-NLS-1$
			DEBUG_OPEN_ERROR_DIALOG = getDebugOption("/debug/internalerror/openDialog"); //$NON-NLS-1$
			DEBUG_GC = getDebugOption("/debug/gc"); //$NON-NLS-1$
			DEBUG_UNDOMONITOR = getDebugOption("/debug/undomonitor"); //$NON-NLS-1$
			DEBUG_CORE_EXCEPTIONS = getDebugOption("/debug/coreExceptions"); //$NON-NLS-1$
		}
	}

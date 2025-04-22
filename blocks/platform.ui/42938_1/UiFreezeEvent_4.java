		if (isStarvedAwake || isStarvedAsleep) {
			String when =
					isStarvedAwake && isStarvedAsleep ?	"awake and asleep" : //$NON-NLS-1$
					isStarvedAwake ? "awake" : "asleep"; //$NON-NLS-1$ //$NON-NLS-2$
			buf.append(", monitoring thread starved for CPU while " + when); //$NON-NLS-1$
		}

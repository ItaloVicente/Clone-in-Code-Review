            return action;
        }
    };

	/**
	 * Workbench action (id: "previousPart", commandId: "org.eclipse.ui.window.previousView"):
	 * Previous part. This action maintains its enablement state.
	 * <p>
	 * <code>NEXT_PART</code> and <code>PREVIOUS_PART</code> form a cycle action pair. For a given
	 * window, use {@link ActionFactory#linkCycleActionPair
	 * ActionFactory.linkCycleActionPair</code>} to connect the two.
	 * </p>

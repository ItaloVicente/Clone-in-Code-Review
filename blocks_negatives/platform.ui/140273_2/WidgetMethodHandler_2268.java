			 * We couldn't find the appropriate method on the current focus
			 * control. It is possible that the current focus control is an
			 * embedded SWT composite, which could be containing some Swing
			 * components. If this is the case, then we should try to pass
			 * through to the underlying Swing component hierarchy. Insha'allah,
			 * this will work.

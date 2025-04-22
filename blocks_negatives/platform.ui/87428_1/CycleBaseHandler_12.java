		table.addTraverseListener(new TraverseListener() {
			/**
			 * Blocks all key traversal events.
			 *
			 * @param event
			 *            The trigger event; must not be <code>null</code>.
			 */
			@Override
			public final void keyTraversed(final TraverseEvent event) {
				event.doit = false;
			}
		});

			} else // We are building the flat map of trigger to commands.
			if (match instanceof Binding) {
				final Binding binding = (Binding) match;
				bindingsByTrigger.put(trigger, binding);
				addReverseLookup(triggersByCommandId, binding
						.getParameterizedCommand(), trigger);

			} else if (match instanceof Collection) {
				final Binding winner = resolveConflicts((Collection) match,
						activeContextTree);
				if (winner == null) {
					conflictsByTrigger.put(trigger, match);
					if (triggerConflicts.add(trigger)) {
						final StringWriter sw = new StringWriter();
						final BufferedWriter buffer = new BufferedWriter(sw);
						try {
							buffer.write("A conflict occurred for "); //$NON-NLS-1$
							buffer.write(trigger.toString());
							buffer.write(':');
							Iterator i = ((Collection) match).iterator();
							while (i.hasNext()) {
								buffer.newLine();
								buffer.write(i.next().toString());

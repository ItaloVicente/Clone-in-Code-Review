						if (DEBUG) {
							Tracing.printTrace("BINDINGS", //$NON-NLS-1$
							Tracing.printTrace("BINDINGS", "    " + match); //$NON-NLS-1$ //$NON-NLS-2$
						}
					} else {
						bindingsByTrigger.put(trigger, winner);
						addReverseLookup(triggersByCommandId, winner
								.getParameterizedCommand(), trigger);

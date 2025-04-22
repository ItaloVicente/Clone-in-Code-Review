					if (oldValue instanceof Binding
							&& newValue instanceof Binding) {
						conflictModel.updateConflictsFor(element,
								((Binding) oldValue).getTriggerSequence(),
								((Binding) newValue).getTriggerSequence(),
								false);

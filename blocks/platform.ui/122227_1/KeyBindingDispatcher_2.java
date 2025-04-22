				} else {
					Collection<Binding> errorMatches = getExecutableMatches(sequenceAfterKeyStroke, context);
					if (errorMatches != null && !errorMatches.isEmpty()) {
						errorSequence = sequenceAfterKeyStroke;
						errorMatch = errorMatches;
					}

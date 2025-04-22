				} else {
					Collection<Binding> errorMatches = getExecutableMatches(sequenceAfterKeyStroke, staticContext);
					if (!errorMatches.isEmpty()) {
						errorSequence = sequenceAfterKeyStroke;
						errorMatch = errorMatches;
					}

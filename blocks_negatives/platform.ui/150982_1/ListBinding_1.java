					try {
						if (clearDestination) {
							destination.clear();
						}
						diff.accept(new ListDiffVisitor<S>() {
							boolean useMoveAndReplace = updateListStrategy.useMoveAndReplace();

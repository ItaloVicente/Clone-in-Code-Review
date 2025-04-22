		destination.getRealm().exec(() -> {
			if (destination == target) {
				updatingTarget = true;
			} else {
				updatingModel = true;
			}
			final MultiStatus multiStatus = BindingStatus.ok();

			try {
				if (clearDestination) {
					destination.clear();
				}
				diff.accept(new ListDiffVisitor<S>() {
					boolean useMoveAndReplace = updateListStrategy.useMoveAndReplace();

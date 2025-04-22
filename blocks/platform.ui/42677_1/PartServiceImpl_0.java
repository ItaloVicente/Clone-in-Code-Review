				if (target.getContext() != null
						&& target.getContext().getParent() == perspective.getContext()) {
					target.getContext().activateBranch();
				} else {
					perspective.getContext().activate();
				}


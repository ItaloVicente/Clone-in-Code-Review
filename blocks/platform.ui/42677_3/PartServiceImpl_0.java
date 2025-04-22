				if (target.getContext() != null && target.getContext().get(MPerspective.class) != null
						&& target.getContext().get(MPerspective.class).getContext() == perspective.getContext()) {
					target.getContext().activateBranch();
				} else {
					perspective.getContext().activate();
				}


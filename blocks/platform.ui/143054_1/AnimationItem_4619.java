		}
	};

	public AnimationItem(AnimationManager animationManager) {
		this.animationManager = animationManager;
	}

	public void createControl(Composite parent) {

		Control animationItem = createAnimationItem(parent);

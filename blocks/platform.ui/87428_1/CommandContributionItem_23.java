		Runnable update = () -> {
			if (commandEvent.isEnabledChanged()
					|| commandEvent.isHandledChanged()) {
				if (visibleEnabled) {
					IContributionManager parent = getParent();
					if (parent != null) {
						parent.update(true);

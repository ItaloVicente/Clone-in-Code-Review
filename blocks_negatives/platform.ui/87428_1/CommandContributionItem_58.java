		Runnable update = new Runnable() {
			@Override
			public void run() {
				if (commandEvent.isEnabledChanged()
						|| commandEvent.isHandledChanged()) {
					if (visibleEnabled) {
						IContributionManager parent = getParent();
						if (parent != null) {
							parent.update(true);
						}
					}
					IHandler handler = commandEvent.getCommand().getHandler();
					if (shouldRestoreAppearance(handler)) {
						label = contributedLabel;
						tooltip = contributedTooltip;
						icon = contributedIcon;
						disabledIcon = contributedDisabledIcon;
						hoverIcon = contributedHoverIcon;

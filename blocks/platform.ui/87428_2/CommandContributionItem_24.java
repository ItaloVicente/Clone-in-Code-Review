				IHandler handler = commandEvent.getCommand().getHandler();
				if (shouldRestoreAppearance(handler)) {
					label = contributedLabel;
					tooltip = contributedTooltip;
					icon = contributedIcon;
					disabledIcon = contributedDisabledIcon;
					hoverIcon = contributedHoverIcon;

					IResource[] current = generator.getSelectedResources();
					if (!Arrays.equals(lastShownResources, current)) {
						builder.scheduleUpdate();
					} else {
						setTitleToolTip(null);
					}
					lastShownResources = null;

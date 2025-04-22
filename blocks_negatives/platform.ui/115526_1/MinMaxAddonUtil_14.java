				minMaxAddon.executeWithIgnoredTagChanges(new Runnable() {

					@Override
					public void run() {
						for (MPartStack partStack : maximizedAreaChildren) {
							partStack.getTags().remove(IPresentationEngine.MAXIMIZED);
							minMaxAddon.adjustCTFButtons(partStack);
						}

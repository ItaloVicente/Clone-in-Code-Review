			@Override
			public boolean getBooleanHint(String key) {
				if (ITriggerPoint.HINT_INTERACTIVE.equals(key)) {
					return true;
				}
				return false;
			}
		});
		IExtensionTracker tracker = PlatformUI.getWorkbench().getExtensionTracker();
		tracker.registerHandler(this, ExtensionTracker.createExtensionPointFilter(getExtensionPointFilter()));

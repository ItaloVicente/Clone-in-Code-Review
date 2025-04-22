		@Override
		public void handleEvent(Event event) {
			switch (event.type) {
			case SWT.PreEvent:
				if (eventHistory != null) {
					eventHistory.recordEvent(event.type);
				}
				nestingLevel++;
				handleEventTransition(true, true);
				break;
			case SWT.PostEvent:
				if (eventHistory != null) {
					eventHistory.recordEvent(event.type);
				}
				nestingLevel--;
				handleEventTransition(true, nestingLevel > 0);
				break;
			case SWT.Sleep:
				if (eventHistory != null) {
					eventHistory.recordEvent(event.type);
				}
				saveAndResetNestingLevel();
				handleEventTransition(true, false);
				break;
			case SWT.Wakeup:
				if (eventHistory != null) {
					eventHistory.recordEvent(event.type);
				}
				restoreNestingLevel();
				handleEventTransition(false, nestingLevel > 0);
				break;
			default:
			}
		}

		private void saveAndResetNestingLevel() {
			if (nestingLevelStackSize >= nestingLevelStack.length) {
				MonitoringPlugin.logError(
						NLS.bind(Messages.EventLoopMonitorThread_max_event_loop_depth_exceeded_1,
						nestingLevelStack.length), null);
				shutdown();
			}
			nestingLevelStack[nestingLevelStackSize++] = nestingLevel;
			nestingLevel = 0;
		}

		private void restoreNestingLevel() {
			if (nestingLevelStackSize > 0) {
				nestingLevel = nestingLevelStack[--nestingLevelStackSize];
			} else {
				nestingLevel = 0;
			}
		}
	}

            PerformanceStats.getStats(events[event], blame).addRun(elapsed, label);
        }
    }

   	/**
   	 * Special hook to signal that application startup is complete and the event
   	 * loop has started running.
   	 */
   	public static void startupComplete() {

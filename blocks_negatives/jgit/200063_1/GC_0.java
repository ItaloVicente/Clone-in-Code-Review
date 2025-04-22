			String processName = java.lang.management.ManagementFactory
					.getRuntimeMXBean().getName();
			if (processName != null && processName.length() > 0) {
				try {
				} catch (Exception e) {
					return 0;
				}
			}

			return 0;

				for (StackTraceElement element : threadInfo.getStackTrace()) {
					if (matchesFilter(element)) {
						return true;
					}
				}
				return false;

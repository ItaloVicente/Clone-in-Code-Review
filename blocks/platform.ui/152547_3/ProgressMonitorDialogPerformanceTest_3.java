			char[] chars = new char[10000];
			for (int i = 0; i < chars.length; i++) {
				chars[i] = 'A';
			}
			final String taskName = new String(chars);

			monitor.setTaskName(taskName);
			processEvents();

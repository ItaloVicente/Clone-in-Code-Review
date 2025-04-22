		for (int i = 0; i < cnt;) {
			final int start = i;
			int end;

			if (cnt - i < estSize) {
				end = cnt;
			} else {
				end = start + estSize;
				int h = list[end - 1].getPathHash();
				while (end < cnt) {
					if (h == list[end].getPathHash())
						end++;
					else
						break;
				}
			}
			i = end;
			taskBlock.tasks.add(new DeltaTask(taskBlock, start, end));
		}

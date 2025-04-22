		@Override
		public void seekPastPrefix(String prefixName) throws IOException {
			List<RefQueueEntry> entiresToAdd = new ArrayList<>();
			for (RefQueueEntry entry : queue) {
				entry.rc.seekPastPrefix(prefixName);
				entiresToAdd.add(entry);
			}
			head.rc.seekPastPrefix(prefixName);

			RefQueueEntry tempHead = head;
			head = null;
			queue.clear();
			add(tempHead);
			for (RefQueueEntry entry : entiresToAdd) {
				add(entry);
			}
		}


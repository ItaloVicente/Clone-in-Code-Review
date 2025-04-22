		@Override
		public void seekPastPrefix(String prefixName) throws IOException {
			List<RefQueueEntry> entriesToAdd = new ArrayList<>();
			entriesToAdd.addAll(queue);
			if (head != null) {
				entriesToAdd.add(head);
			}

			head = null;
			queue.clear();

			for(RefQueueEntry entry : entriesToAdd){
				entry.rc.seekPastPrefix(prefixName);
				add(entry);
			}
		}


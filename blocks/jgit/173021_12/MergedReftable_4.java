		@Override
		public void seekPastPrefix(String prefixName) throws IOException {
			List<RefQueueEntry> entriesToAdd = new ArrayList<>();
			entriesToAdd.addAll(queue);
			entriesToAdd.add(head);

			head = null;
			queue.clear();

			for(RefQueueEntry entry : entriesToAdd){
				entry.rc.seekPastPrefix(prefixName);
				add(entry);
			}
		}


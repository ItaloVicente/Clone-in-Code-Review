		private void pushHeldRegions() {
			for (int i = held.size() - 1; i >= 0; i--)
				queue.add(held.get(i));
			held.clear();
		}


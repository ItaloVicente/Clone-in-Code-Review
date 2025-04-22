		void diffRegion(Edit r) {
			diffReplace(r);
			while (!queue.isEmpty())
				diff(queue.remove(queue.size() - 1));
		}

		private void diffReplace(Edit r) {

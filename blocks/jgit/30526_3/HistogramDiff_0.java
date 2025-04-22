		void diffRegion(Edit r) {
			diffReplace(r);
			while (!queue.isEmpty())
				diffReplace(queue.remove(queue.size() - 1));
		}

		private void diffReplace(Edit r) {

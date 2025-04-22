		void diffRegion(Edit r) {
			diffReplace(r);
			while (!queue.isEmpty())
				diffReplace(queue.removeFirst());
		}

		private void diffReplace(Edit r) {

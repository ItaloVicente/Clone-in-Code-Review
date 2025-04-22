		if (a != null) {
			this.and = a;
			this.requiresCommitBody = a.requiresCommitBody();
		}
		if (this.maxCount == 0) {
			this.stop = true;
		}

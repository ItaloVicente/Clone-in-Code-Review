package org.eclipse.jgit.lib;

import org.eclipse.jgit.annotations.Nullable;

public interface OpenSubmission {

	@Nullable
	SubmissionInfo getSubmissionInfo();

	void completed();

	OpenSubmission NOOP = new OpenSubmission() {

		@Override
		public SubmissionInfo getSubmissionInfo() {
			return null;
		}

		@Override
		public void completed() {
		}
	};
}

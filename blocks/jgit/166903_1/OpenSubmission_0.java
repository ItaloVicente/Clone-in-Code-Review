package org.eclipse.jgit.lib;

import java.util.Map;

import org.eclipse.jgit.annotations.Nullable;
import org.eclipse.jgit.transport.ReceiveCommand;

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
		public void completed() {}
	};
}

package org.eclipse.jgit.api;

import org.eclipse.jgit.transport.FetchResult;

public class PullResult {
	private final FetchResult fetchResult;

	private final MergeResult mergeResult;

	private final String fetchedFrom;

	PullResult(FetchResult fetchResult
			MergeResult mergeResult) {
		this.fetchResult = fetchResult;
		this.fetchedFrom = fetchedFrom;
		this.mergeResult = mergeResult;
	}

	public FetchResult getFetchResult() {
		return this.fetchResult;
	}

	public MergeResult getMergeResult() {
		return this.mergeResult;
	}

	public String getFetchedFrom() {
		return this.fetchedFrom;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		if (fetchResult != null)
			sb.append(fetchResult.toString());
		else
			sb.append("No fetch result");
		sb.append("\n");
		if (mergeResult != null)
			sb.append(mergeResult.toString());
		else
			sb.append("No merge result");
		return sb.toString();
	}
}

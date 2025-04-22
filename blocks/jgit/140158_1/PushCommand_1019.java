package org.eclipse.jgit.api;

import org.eclipse.jgit.transport.FetchResult;

public class PullResult {
	private final FetchResult fetchResult;

	private final MergeResult mergeResult;

	private final RebaseResult rebaseResult;

	private final String fetchedFrom;

	PullResult(FetchResult fetchResult
			MergeResult mergeResult) {
		this.fetchResult = fetchResult;
		this.fetchedFrom = fetchedFrom;
		this.mergeResult = mergeResult;
		this.rebaseResult = null;
	}

	PullResult(FetchResult fetchResult
			RebaseResult rebaseResult) {
		this.fetchResult = fetchResult;
		this.fetchedFrom = fetchedFrom;
		this.mergeResult = null;
		this.rebaseResult = rebaseResult;
	}

	public FetchResult getFetchResult() {
		return this.fetchResult;
	}

	public MergeResult getMergeResult() {
		return this.mergeResult;
	}

	public RebaseResult getRebaseResult() {
		return this.rebaseResult;
	}

	public String getFetchedFrom() {
		return this.fetchedFrom;
	}

	public boolean isSuccessful() {
		if (mergeResult != null)
			return mergeResult.getMergeStatus().isSuccessful();
		else if (rebaseResult != null)
			return rebaseResult.getStatus().isSuccessful();
		return true;
	}

	@SuppressWarnings("nls")
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
		else if (rebaseResult != null)
			sb.append(rebaseResult.toString());
		else
			sb.append("No update result");
		return sb.toString();
	}
}

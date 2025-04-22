package org.eclipse.egit.core.op;

import org.eclipse.jgit.transport.FetchResult;
import org.eclipse.jgit.transport.URIish;

public class FetchOperationResult {
	private final URIish uri;

	private final FetchResult fetchResult;

	private final String fetchErrorMessage;

	public FetchOperationResult(URIish uri, FetchResult result) {
		this.uri = uri;
		this.fetchResult = result;
		this.fetchErrorMessage = null;
	}

	public FetchOperationResult(URIish uri, String errorMessage) {
		this.uri = uri;
		this.fetchResult = null;
		this.fetchErrorMessage = errorMessage;
	}

	public URIish getURI() {
		return uri;
	}

	public FetchResult getFetchResult() {
		return fetchResult;
	}

	public String getErrorMessage() {
		return fetchErrorMessage;
	}
}

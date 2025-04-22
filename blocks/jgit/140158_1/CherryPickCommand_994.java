package org.eclipse.jgit.api;

import java.util.ArrayList;
import java.util.List;

public class CheckoutResult {

	public static final CheckoutResult ERROR_RESULT = new CheckoutResult(
			Status.ERROR

	public static final CheckoutResult NOT_TRIED_RESULT = new CheckoutResult(
			Status.NOT_TRIED

	public enum Status {
		NOT_TRIED
		OK
		CONFLICTS
		NONDELETED
		ERROR;
	}

	private final Status myStatus;

	private final List<String> conflictList;

	private final List<String> undeletedList;

	private final List<String> modifiedList;

	private final List<String> removedList;

	CheckoutResult(Status status
		this(status
	}

	CheckoutResult(Status status
			List<String> removed) {
		myStatus = status;
		if (status == Status.CONFLICTS)
			this.conflictList = fileList;
		else
			this.conflictList = new ArrayList<>(0);
		if (status == Status.NONDELETED)
			this.undeletedList = fileList;
		else
			this.undeletedList = new ArrayList<>(0);

		this.modifiedList = modified;
		this.removedList = removed;
	}

	CheckoutResult(List<String> modified
		myStatus = Status.OK;

		this.conflictList = new ArrayList<>(0);
		this.undeletedList = new ArrayList<>(0);

		this.modifiedList = modified;
		this.removedList = removed;
	}

	public Status getStatus() {
		return myStatus;
	}

	public List<String> getConflictList() {
		return conflictList;
	}

	public List<String> getUndeletedList() {
		return undeletedList;
	}

	public List<String> getModifiedList() {
		return modifiedList;
	}

	public List<String> getRemovedList() {
		return removedList;
	}
}

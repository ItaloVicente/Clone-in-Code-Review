package org.eclipse.jgit.api;

import java.util.ArrayList;
import java.util.List;

public class CheckoutResult {

	public static CheckoutResult OK_RESULT = new CheckoutResult(Status.OK

	public static CheckoutResult ERROR_RESULT = new CheckoutResult(
			Status.ERROR

	public static CheckoutResult NOT_TRIED_RESULT = new CheckoutResult(
			Status.NOT_TRIED

	public enum Status {
		NOT_TRIED
		OK
		CONFLICTS
		UNDELETEDFILES
		ERROR;
	}

	private final Status myStatus;

	private final List<String> conflictList;

	private final List<String> undeletedList;

	CheckoutResult(Status status
		myStatus = status;
		if (status == Status.CONFLICTS)
			this.conflictList = fileList;
		else
			this.conflictList = new ArrayList<String>(0);
		if (status == Status.UNDELETEDFILES)
			this.undeletedList = fileList;
		else
			this.undeletedList = new ArrayList<String>(0);

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

}

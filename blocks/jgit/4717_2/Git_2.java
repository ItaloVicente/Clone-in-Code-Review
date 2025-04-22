package org.eclipse.jgit.api;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.jgit.patch.ApplyError;
import org.eclipse.jgit.patch.FormatError;

public class ApplyResult {

	private List<FormatError> formatErrors = Collections.emptyList();

	private List<ApplyError> applyErrors = new ArrayList<ApplyError>();

	public ApplyResult setFormatErrors(List<FormatError> formatErrors) {
		this.formatErrors = formatErrors;
		return this;
	}

	public List<FormatError> getFormatErrors() {
		return formatErrors;
	}

	public ApplyResult addApplyError(ApplyError applyError) {
		applyErrors.add(applyError);
		return this;
	}

	public List<ApplyError> getApplyErrors() {
		return applyErrors;
	}

}

package org.eclipse.ui.tests.performance;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.core.runtime.content.IContentDescriber;
import org.eclipse.core.runtime.content.IContentDescription;
import org.eclipse.core.runtime.content.ITextContentDescriber;


public final class ContentDescriberForTestsOnly implements ITextContentDescriber {

	private static final int SIMULATED_CALCULATION_TIME = 75;
	private static final QualifiedName[] SUPPORTED_OPTIONS = {IContentDescription.CHARSET, IContentDescription.BYTE_ORDER_MARK};

	public int describe(InputStream contents, IContentDescription description) throws IOException {
		int result = IContentDescriber.INDETERMINATE;

		if (description == null) {
			result = computeValidity(contents);
		}
		else {
			calculateSupportedOptions(contents, description);
			result = computeValidity(contents);
		}

		return result;
	}

	public int describe(Reader contents, IContentDescription description) throws IOException {
		int result = IContentDescriber.INDETERMINATE;

		if (description == null) {
			result = computeValidity(contents);
		}
		else {
			calculateSupportedOptions(contents, description);
			result = computeValidity(contents);
		}

		return result;
	}

	public QualifiedName[] getSupportedOptions() {

		return SUPPORTED_OPTIONS;
	}

	private void calculateSupportedOptions(InputStream contents, IContentDescription description) throws IOException {
		if (isRelevent(description)) {
			makeBusy();
		}
	}

	private void makeBusy() {
		try {
			Thread.sleep(SIMULATED_CALCULATION_TIME);
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void calculateSupportedOptions(Reader contents, IContentDescription description) throws IOException {
		if (isRelevent(description)) {
			makeBusy();
		}
	}

	private int computeValidity(InputStream inputStream) {
		return IContentDescriber.INDETERMINATE;
	}

	private int computeValidity(Reader reader) {
		return IContentDescriber.INDETERMINATE;
	}

	private boolean isRelevent(IContentDescription description) {
		boolean result = false;
		if (description == null)
			result = false;
		else if (description.isRequested(IContentDescription.BYTE_ORDER_MARK))
			result = true;
		else if (description.isRequested(IContentDescription.CHARSET))
			result = true;
		return result;
	}

}

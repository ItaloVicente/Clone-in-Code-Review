package org.eclipse.jgit.transport;

import org.eclipse.jgit.transport.UploadPack.RequestValidator;

public class ReachableCommitTipRequestValidatorTest
		extends RequestValidatorTestCase {

	@Override
	protected RequestValidator createValidator() {
		return new UploadPack.ReachableCommitTipRequestValidator();
	}

	@Override
	protected boolean isReachableCommitValid() {
		return true;
	}

	@Override
	protected boolean isUnreachableCommitValid() {
		return false;
	}

	@Override
	protected boolean isAdvertisedTipValid() {
		return true;
	}

	@Override
	protected boolean isReachableBlobValid_withBitmaps() {
		return true;
	}

	@Override
	protected boolean isReachableBlobValid_withoutBitmaps() {
		return false;
	}

	@Override
	protected boolean isUnreachableBlobValid() {
		return false;
	}

	@Override
	protected boolean isUnadvertisedTipCommitValid() {
		return true;
	}

}

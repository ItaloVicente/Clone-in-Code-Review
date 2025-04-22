package org.eclipse.egit.gitflow.internal;

import org.eclipse.osgi.util.NLS;

public class CoreText extends NLS {

	private static final String BUNDLE_NAME = "org.eclipse.egit.gitflow.internal.coretext"; //$NON-NLS-1$


	static {
		initializeMessages(BUNDLE_NAME, CoreText.class);
	}

	public static String AbstractFeatureOperation_notOnAFeautreBranch;

	public static String AbstractHotfixOperation_notOnAHotfixBranch;

	public static String AbstractReleaseOperation_notOnAReleaseBranch;

	public static String AbstractVersionFinishOperation_tagNameExists;

	public static String FeatureListOperation_unableToParse;

	public static String pushToRemoteFailed;

	public static String unableToStoreGitConfig;

	public static String FeatureRebaseOperation_notOnAFeatureBranch;

	public static String FeatureStartOperation_notOn;

	public static String FeatureTrackOperation_checkoutReturned;

	public static String FeatureTrackOperation_localBranchExists;

	public static String FeatureTrackOperation_unableToStoreGitConfig;

	public static String GitFlowOperation_branchMissing;

	public static String GitFlowOperation_branchNotFound;

	public static String GitFlowOperation_unableToCheckout;

	public static String HotfixFinishOperation_hotfix;

	public static String HotfixFinishOperation_mergeFromHotfixToMasterFailed;

	public static String InitOperation_initialCommit;

	public static String ReleaseFinishOperation_releaseOf;

	public static String ReleaseStartOperation_notOn;

	public static String ReleaseStartOperation_releaseNameAlreadyExists;

}

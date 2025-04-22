package org.eclipse.egit.gitflow.ui.internal;

import org.eclipse.osgi.util.NLS;

public class UIText extends NLS {

	private static final String BUNDLE_NAME = "org.eclipse.egit.gitflow.ui.internal.uitext"; //$NON-NLS-1$

	static {
		initializeMessages(BUNDLE_NAME, UIText.class);
	}

	public static String FeatureFinishHandler_finishingFeature;

	public static String FeatureRebaseHandler_conflicts;

	public static String FeatureRebaseHandler_rebaseFailed;

	public static String FeatureRebaseHandler_rebasingFeature;

	public static String FeatureRebaseHandler_resolveConflictsManually;

	public static String FeatureStartHandler_pleaseProvideANameForTheNewFeature;

	public static String FeatureStartHandler_provideFeatureName;

	public static String FeatureStartHandler_startingNewFeature;

	public static String FeatureTrackHandler_fetchingRemoteFeatures;

	public static String FeatureTrackHandler_noRemoteFeatures;

	public static String FeatureTrackHandler_noRemoteFeaturesFoundOnTheConfiguredRemote;

	public static String FeatureTrackHandler_remoteFeatures;

	public static String FeatureTrackHandler_selectFeature;

	public static String FeatureTrackHandler_trackingFeature;

	public static String HotfixFinishHandler_finishingHotfix;

	public static String HotfixStartHandler_pleaseProvideANameForTheNewHotfix;

	public static String HotfixStartHandler_provideHotfixName;

	public static String HotfixStartHandler_startingNewHotfix;

	public static String InitHandler_initializing;

	public static String ReleaseFinishHandler_finishingRelease;

	public static String ReleaseStartHandler_provideANameForTheNewRelease;

	public static String ReleaseStartHandler_provideReleaseName;

	public static String ReleaseStartHandler_startingNewRelease;

	public static String FeatureCheckoutHandler_checkingOutFeature;

	public static String FeatureCheckoutHandler_localFeatures;

	public static String FeatureCheckoutHandler_selectFeature;

	public static String FeaturePublishHandler_publishingFeature;

	public static String ReleasePublishHandler_publishingRelease;

	public static String HotfixPublishHandler_publishingHotfix;

	public static String NameValidator_invalidName;

	public static String NameValidator_nameAlreadyExists;

	public static String AbstractRebaseCommandHandler_cleanupDialog_title;

	public static String AbstractRebaseCommandHandler_cleanupDialog_text;
}

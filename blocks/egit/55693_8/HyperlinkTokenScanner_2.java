package org.eclipse.egit.ui.internal.dialogs;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.hyperlink.IHyperlink;
import org.eclipse.jface.text.hyperlink.IHyperlinkDetector;
import org.eclipse.jface.text.hyperlink.IHyperlinkDetectorExtension;
import org.eclipse.jface.text.hyperlink.IHyperlinkDetectorExtension2;
import org.eclipse.jface.text.source.IOverviewRuler;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.IVerticalRuler;
import org.eclipse.jface.text.source.SourceViewer;
import org.eclipse.jface.text.source.SourceViewerConfiguration;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.editors.text.EditorsUI;
import org.eclipse.ui.editors.text.TextSourceViewerConfiguration;
import org.eclipse.ui.texteditor.AbstractTextEditor;
import org.eclipse.ui.texteditor.HyperlinkDetectorDescriptor;

public class HyperlinkSourceViewer extends SourceViewer {

	private Configuration configuration;

	private Set<String> preferenceKeysForEnablement;

	private Set<String> preferenceKeysForActivation;

	private IPropertyChangeListener hyperlinkChangeListener;

	public HyperlinkSourceViewer(Composite parent, IVerticalRuler ruler,
			int styles) {
		super(parent, ruler, styles);
	}

	public HyperlinkSourceViewer(Composite parent, IVerticalRuler verticalRuler,
			IOverviewRuler overviewRuler, boolean showAnnotationsOverview,
			int styles) {
		super(parent, verticalRuler, overviewRuler, showAnnotationsOverview,
				styles);
	}

	@Override
	public void configure(SourceViewerConfiguration config) {
		super.configure(config);
		if (config instanceof Configuration) {
			configuration = (Configuration) config;
			configurePreferenceKeys();
			hyperlinkChangeListener = new IPropertyChangeListener() {
				@Override
				public void propertyChange(PropertyChangeEvent event) {
					String property = event.getProperty();
					if (preferenceKeysForEnablement.contains(property)) {
						resetHyperlinkDetectors();
						final Control control = getControl();
						if (control != null && !control.isDisposed()) {
							control.getDisplay().asyncExec(new Runnable() {
								@Override
								public void run() {
									if (!control.isDisposed()) {
										refresh();
									}
								}
							});
						}
					} else if (preferenceKeysForActivation.contains(property)) {
						resetHyperlinkDetectors();
					}
				}
			};
			EditorsUI.getPreferenceStore()
					.addPropertyChangeListener(hyperlinkChangeListener);
		} else {
			configuration = null;
			hyperlinkChangeListener = null;
		}
	}

	private void configurePreferenceKeys() {
		preferenceKeysForEnablement = new HashSet<>();
		preferenceKeysForActivation = new HashSet<>();
		preferenceKeysForEnablement
				.add(AbstractTextEditor.PREFERENCE_HYPERLINKS_ENABLED);
		preferenceKeysForActivation
				.add(AbstractTextEditor.PREFERENCE_HYPERLINK_KEY_MODIFIER);
		Map targets = configuration.getHyperlinkDetectorTargets(this);
		for (HyperlinkDetectorDescriptor desc : EditorsUI
				.getHyperlinkDetectorRegistry()
				.getHyperlinkDetectorDescriptors()) {
			if (targets.keySet().contains(desc.getTargetId())) {
				preferenceKeysForEnablement.add(desc.getId());
				preferenceKeysForActivation.add(desc.getId()
						+ HyperlinkDetectorDescriptor.STATE_MASK_POSTFIX);
			}
		}
	}

	private void resetHyperlinkDetectors() {
		IHyperlinkDetector[] detectors = configuration
				.getHyperlinkDetectors(this);
		int stateMask = configuration.getHyperlinkStateMask(this);
		setHyperlinkDetectors(detectors, stateMask);
	}

	@Override
	public void unconfigure() {
		super.unconfigure();
		if (hyperlinkChangeListener != null) {
			EditorsUI.getPreferenceStore()
					.removePropertyChangeListener(hyperlinkChangeListener);
		}
		preferenceKeysForEnablement = null;
		preferenceKeysForActivation = null;
	}

	public static class Configuration extends TextSourceViewerConfiguration {

		public Configuration() {
			super();
		}

		public Configuration(IPreferenceStore preferenceStore) {
			super(preferenceStore);
		}

		@Override
		public final IHyperlinkDetector[] getHyperlinkDetectors(
				ISourceViewer sourceViewer) {
			IHyperlinkDetector[] detectors = internalGetHyperlinkDetectors(
					sourceViewer);
			if (detectors != null && detectors.length > 0
					&& getHyperlinkStateMask(sourceViewer) == SWT.NONE) {
				int defaultMask = getConfiguredDefaultMask();
				IHyperlinkDetector[] newDetectors = new IHyperlinkDetector[detectors.length
				int j = 0;
				for (IHyperlinkDetector original : detectors) {
					if (original instanceof IHyperlinkDetectorExtension2) {
						int mask = ((IHyperlinkDetectorExtension2) original)
								.getStateMask();
						if (mask == -1) {
							newDetectors[j++] = new FixedMaskHyperlinkDetector(
									original, defaultMask);
							if (defaultMask != SWT.NONE) {
								newDetectors[j++] = new NoMaskHyperlinkDetector(
										original);
							}
						} else {
							newDetectors[j++] = original;
							if (mask != SWT.NONE) {
								newDetectors[j++] = new NoMaskHyperlinkDetector(
										original);
							}
						}
					} else {
						newDetectors[j++] = original;
					}
				}
				IHyperlinkDetector[] result = new IHyperlinkDetector[j];
				System.arraycopy(newDetectors, 0, result, 0, j);
				return result;
			}
			return detectors;
		}

		protected @Nullable IHyperlinkDetector[] internalGetHyperlinkDetectors(
				ISourceViewer sourceViewer) {
			return super.getHyperlinkDetectors(sourceViewer);
		}

		@Override
		protected Map getHyperlinkDetectorTargets(ISourceViewer sourceViewer) {
			return super.getHyperlinkDetectorTargets(sourceViewer);
		}

	}

	private static abstract class InternalHyperlinkDetector
			implements IHyperlinkDetector, IHyperlinkDetectorExtension,
			IHyperlinkDetectorExtension2 {

		protected IHyperlinkDetector delegate;

		protected InternalHyperlinkDetector(IHyperlinkDetector delegate) {
			this.delegate = delegate;
		}

		@Override
		public final IHyperlink[] detectHyperlinks(ITextViewer textViewer,
				IRegion region, boolean canShowMultipleHyperlinks) {
			return delegate.detectHyperlinks(textViewer, region,
					canShowMultipleHyperlinks);
		}

		@Override
		public final void dispose() {
			if (delegate instanceof IHyperlinkDetectorExtension) {
				((IHyperlinkDetectorExtension) delegate).dispose();
			}
		}
	}

	private static class FixedMaskHyperlinkDetector
			extends InternalHyperlinkDetector {

		private final int mask;

		protected FixedMaskHyperlinkDetector(IHyperlinkDetector delegate,
				int mask) {
			super(delegate);
			this.mask = mask;
		}

		@Override
		public int getStateMask() {
			return mask;
		}
	}

	protected static class NoMaskHyperlinkDetector
			extends FixedMaskHyperlinkDetector {

		private NoMaskHyperlinkDetector(IHyperlinkDetector delegate) {
			super(delegate, SWT.NONE);
		}

	}

	private static int getConfiguredDefaultMask() {
		int mask = computeStateMask(EditorsUI.getPreferenceStore().getString(
				AbstractTextEditor.PREFERENCE_HYPERLINK_KEY_MODIFIER));
		if (mask == -1) {
			mask = EditorsUI.getPreferenceStore().getInt(
					AbstractTextEditor.PREFERENCE_HYPERLINK_KEY_MODIFIER_MASK);
		}
		return mask;
	}


	private static final int findLocalizedModifier(String modifierName) {
		if (modifierName == null) {
			return 0;
		}

		if (modifierName
				.equalsIgnoreCase(Action.findModifierString(SWT.CTRL))) {
			return SWT.CTRL;
		}
		if (modifierName
				.equalsIgnoreCase(Action.findModifierString(SWT.SHIFT))) {
			return SWT.SHIFT;
		}
		if (modifierName.equalsIgnoreCase(Action.findModifierString(SWT.ALT))) {
			return SWT.ALT;
		}
		if (modifierName
				.equalsIgnoreCase(Action.findModifierString(SWT.COMMAND))) {
			return SWT.COMMAND;
		}

		return 0;
	}

	private static final int computeStateMask(String modifiers) {
		if (modifiers == null) {
			return -1;
		}

		if (modifiers.length() == 0) {
			return SWT.NONE;
		}

		int stateMask = 0;
		StringTokenizer modifierTokenizer = new StringTokenizer(modifiers,
				",;.:+-* "); //$NON-NLS-1$
		while (modifierTokenizer.hasMoreTokens()) {
			int modifier = findLocalizedModifier(modifierTokenizer.nextToken());
			if (modifier == 0 || (stateMask & modifier) == modifier) {
				return -1;
			}
			stateMask = stateMask | modifier;
		}
		return stateMask;
	}

}

package org.eclipse.egit.ui.internal.dialogs;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.text.hyperlink.IHyperlinkDetector;
import org.eclipse.jface.text.source.IOverviewRuler;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.IVerticalRuler;
import org.eclipse.jface.text.source.SourceViewer;
import org.eclipse.jface.text.source.SourceViewerConfiguration;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
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
						Control control = getControl();
						if (control != null && !control.isDisposed()) {
							control.getDisplay().asyncExec(new Runnable() {
								@Override
								public void run() {
									refresh();
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
		protected Map getHyperlinkDetectorTargets(ISourceViewer sourceViewer) {
			return super.getHyperlinkDetectorTargets(sourceViewer);
		}
	}
}

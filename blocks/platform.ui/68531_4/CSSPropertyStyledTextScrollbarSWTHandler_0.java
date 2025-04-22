package org.eclipse.e4.ui.css.swt.dom;

import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.IEclipsePreferences.IPreferenceChangeListener;
import org.eclipse.core.runtime.preferences.IEclipsePreferences.PreferenceChangeEvent;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.e4.ui.css.core.engine.CSSEngine;
import org.eclipse.e4.ui.internal.css.swt.CSSActivator;
import org.eclipse.e4.ui.internal.css.swt.dom.scrollbar.StyledTextThemedScrollBarAdapter;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Composite;
import org.osgi.service.log.LogService;
import org.osgi.service.prefs.Preferences;

public class StyledTextElement extends CompositeElement {

	private SyncScrollBarThemedPreference fSyncScrollBarThemedPreference;

	public StyledTextElement(Composite composite, CSSEngine engine) {
		super(composite, engine);
	}

	public StyledText getStyledText() {
		return (StyledText) getControl();
	}

	private StyledTextThemedScrollBarAdapter getScrollbarAdapter() {
		return StyledTextThemedScrollBarAdapter.getScrollbarAdapter(getStyledText());
	}

	public void setScrollBarBackgroundColor(Color newColor) {
		StyledTextThemedScrollBarAdapter scrollbarAdapter = getScrollbarAdapter();
		if (scrollbarAdapter != null) {
			scrollbarAdapter.setScrollBarBackgroundColor(newColor);
		}
	}

	public void setScrollBarForegroundColor(Color newColor) {
		StyledTextThemedScrollBarAdapter scrollbarAdapter = getScrollbarAdapter();
		if (scrollbarAdapter != null) {
			scrollbarAdapter.setScrollBarForegroundColor(newColor);
		}
	}

	public void setScrollBarWidth(int width) {
		StyledTextThemedScrollBarAdapter scrollbarAdapter = getScrollbarAdapter();
		if (scrollbarAdapter != null) {
			scrollbarAdapter.setScrollBarWidth(width);
		}
	}

	public void setMouseNearScrollScrollBarWidth(int width) {
		StyledTextThemedScrollBarAdapter scrollbarAdapter = getScrollbarAdapter();
		if (scrollbarAdapter != null) {
			scrollbarAdapter.setMouseNearScrollScrollBarWidth(width);
		}
	}

	public void setVerticalScrollBarVisible(boolean visible) {
		StyledTextThemedScrollBarAdapter scrollbarAdapter = getScrollbarAdapter();
		if (scrollbarAdapter != null) {
			scrollbarAdapter.setVerticalScrollBarVisible(visible);
		}
	}

	public void setHorizontalScrollBarVisible(boolean visible) {
		StyledTextThemedScrollBarAdapter scrollbarAdapter = getScrollbarAdapter();
		if (scrollbarAdapter != null) {
			scrollbarAdapter.setHorizontalScrollBarVisible(visible);
		}
	}

	public void setScrollBarBorderRadius(int radius) {
		StyledTextThemedScrollBarAdapter scrollbarAdapter = getScrollbarAdapter();
		if (scrollbarAdapter != null) {
			scrollbarAdapter.setScrollBarBorderRadius(radius);
		}
	}

	private void setScrollBarThemed(boolean themed) {
		StyledTextThemedScrollBarAdapter scrollbarAdapter = getScrollbarAdapter();
		if (scrollbarAdapter != null) {
			scrollbarAdapter.setScrollBarThemed(themed);
		}
	}

	@Override
	public void reset() {
		super.reset();
		disposePreferenceChangeListener();
		setScrollBarThemed(false);
	}

	@Override
	public void dispose() {
		super.dispose();
		disposePreferenceChangeListener();
	}

	public void setScrollBarThemed(String cssText) {
		if (cssText.equals("true")) { //$NON-NLS-1$
			setScrollBarThemed(true);
			disposePreferenceChangeListener();

		} else if (cssText.equals("false")) { //$NON-NLS-1$
			setScrollBarThemed(false);
			disposePreferenceChangeListener();

		} else if (cssText.startsWith("preference:")) { //$NON-NLS-1$
			cssText = cssText.substring("preference:".length()); //$NON-NLS-1$
			int index;
			if ((index = cssText.indexOf('/')) != -1) {
				String qualifier = cssText.substring(0, index);
				String key = cssText.substring(index + 1);
				keepPreferenceSynchronized(qualifier, key);
				return;
			}
			disposePreferenceChangeListener();

		} else {
			CSSActivator.getDefault().log(LogService.LOG_WARNING,
					"Don't know how to handle setting value: " + cssText //$NON-NLS-1$
					+ " (supported: boolean or preference:bundle.qualifier.id/key)."); //$NON-NLS-1$
		}

	}

	private void disposePreferenceChangeListener() {
		if (fSyncScrollBarThemedPreference != null) {
			fSyncScrollBarThemedPreference.dispose();
			fSyncScrollBarThemedPreference = null;
		}
	}

	private static class SyncScrollBarThemedPreference implements IPreferenceChangeListener {

		public final String qualifier;
		public final String key;
		private StyledTextElement styledTextElement;

		public SyncScrollBarThemedPreference(String qualifier, String key, StyledTextElement styledTextElement) {
			this.qualifier = qualifier;
			this.key = key;
			this.styledTextElement = styledTextElement;

			IEclipsePreferences node = InstanceScope.INSTANCE.getNode(qualifier);
			boolean setThemed = node.getBoolean(key, false);
			this.styledTextElement.setScrollBarThemed(setThemed);
			node.addPreferenceChangeListener(this);
		}

		public void dispose() {
			IEclipsePreferences node = InstanceScope.INSTANCE.getNode(qualifier);
			node.removePreferenceChangeListener(this);
		}

		@Override
		public void preferenceChange(PreferenceChangeEvent event) {
			Preferences node = event.getNode();
			StyledText styledText = this.styledTextElement.getStyledText();
			if (styledText.isDisposed()) {
				this.styledTextElement.disposePreferenceChangeListener();
			} else {
				this.styledTextElement.setScrollBarThemed(node.getBoolean(key, false));
			}
		}
	}

	private void keepPreferenceSynchronized(final String qualifier, String key) {
		if (fSyncScrollBarThemedPreference != null) {
			if (!fSyncScrollBarThemedPreference.qualifier.equals(qualifier)
					|| !fSyncScrollBarThemedPreference.key.equals(key)) {
				disposePreferenceChangeListener();
			} else {
				return; // It's still the same qualifier/key for the
			}
		}

		if (fSyncScrollBarThemedPreference == null) {
			fSyncScrollBarThemedPreference = new SyncScrollBarThemedPreference(qualifier, key, this);
			StyledText styledText = this.getStyledText();
			if (!styledText.isDisposed()) {
				styledText.addDisposeListener(new DisposeListener() {

					@Override
					public void widgetDisposed(DisposeEvent e) {
						disposePreferenceChangeListener();
					}
				});
			}
		}
	}
}

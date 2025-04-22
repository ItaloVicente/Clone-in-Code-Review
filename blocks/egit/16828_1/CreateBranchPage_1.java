package org.eclipse.egit.ui.internal.components;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.egit.core.op.CreateLocalBranchOperation.UpstreamConfig;
import org.eclipse.egit.ui.UIUtils;
import org.eclipse.egit.ui.internal.UIText;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;

public class UpstreamConfigComponent {

	public interface UpstreamConfigSelectionListener {
		public void upstreamConfigSelected(UpstreamConfig upstreamConfig);
	}

	private final Composite container;

	private Button configureUpstreamCheck;

	private Button mergeRadio;

	private Button rebaseRadio;

	private List<UpstreamConfigSelectionListener> listeners = new ArrayList<UpstreamConfigSelectionListener>();

	private Group upstreamConfigGroup;

	public UpstreamConfigComponent(Composite parent, int style) {
		container = new Composite(parent, style);
		container.setLayout(GridLayoutFactory.fillDefaults()
				.extendedMargins(0, 0, 0, 10).create());

		configureUpstreamCheck = new Button(container, SWT.CHECK);
		configureUpstreamCheck.setLayoutData(GridDataFactory.fillDefaults()
				.grab(true, false).create());
		configureUpstreamCheck
				.setText(UIText.UpstreamConfigComponent_ConfigureUpstreamCheck);
		configureUpstreamCheck
				.setToolTipText(UIText.UpstreamConfigComponent_ConfigureUpstreamToolTip);
		configureUpstreamCheck.setSelection(true);

		upstreamConfigGroup = new Group(container, SWT.SHADOW_ETCHED_IN);
		upstreamConfigGroup.setLayoutData(GridDataFactory.fillDefaults()
				.grab(true, false).indent(UIUtils.getControlIndent(), 0)
				.create());
		upstreamConfigGroup.setLayout(GridLayoutFactory.swtDefaults().create());
		upstreamConfigGroup
				.setText(UIText.UpstreamConfigComponent_PullGroup);

		mergeRadio = new Button(upstreamConfigGroup, SWT.RADIO);
		mergeRadio.setText(UIText.UpstreamConfigComponent_MergeRadio);
		mergeRadio.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				upstreamConfigSelected();
			}
		});
		mergeRadio.setSelection(true);

		rebaseRadio = new Button(upstreamConfigGroup, SWT.RADIO);
		rebaseRadio.setText(UIText.UpstreamConfigComponent_RebaseRadio);
		rebaseRadio.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				upstreamConfigSelected();
			}
		});

		configureUpstreamCheck.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				updateEnabled();
				upstreamConfigSelected();
			}
		});
	}

	public Composite getContainer() {
		return container;
	}

	public void addUpstreamConfigSelectionListener(
			UpstreamConfigSelectionListener listener) {
		listeners.add(listener);
	}

	public void setUpstreamConfig(UpstreamConfig upstreamConfig) {
		if (upstreamConfig == UpstreamConfig.NONE) {
			configureUpstreamCheck.setSelection(false);
		} else {
			configureUpstreamCheck.setSelection(true);
			mergeRadio.setSelection(upstreamConfig == UpstreamConfig.MERGE);
			rebaseRadio.setSelection(upstreamConfig == UpstreamConfig.REBASE);
		}
		updateEnabled();
	}

	private void upstreamConfigSelected() {
		UpstreamConfig config = getSelectedUpstreamConfig();
		for (UpstreamConfigSelectionListener listener : listeners)
			listener.upstreamConfigSelected(config);
	}

	private UpstreamConfig getSelectedUpstreamConfig() {
		if (!configureUpstreamCheck.getSelection())
			return UpstreamConfig.NONE;
		else if (mergeRadio.getSelection())
			return UpstreamConfig.MERGE;
		else if (rebaseRadio.getSelection())
			return UpstreamConfig.REBASE;
		return UpstreamConfig.NONE;
	}

	private void updateEnabled() {
		boolean enabled = configureUpstreamCheck.getSelection();
		upstreamConfigGroup.setEnabled(enabled);
		mergeRadio.setEnabled(enabled);
		rebaseRadio.setEnabled(enabled);
	}
}

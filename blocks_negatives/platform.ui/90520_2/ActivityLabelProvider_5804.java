/*******************************************************************************
 * Copyright (c) 2003, 2016 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.ui.internal.activities.ws;

import static org.eclipse.swt.events.SelectionListener.widgetSelectedAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.AbstractTreeViewer;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.CheckboxTreeViewer;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.FontMetrics;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.activities.ActivitiesPreferencePage;
import org.eclipse.ui.activities.IActivity;
import org.eclipse.ui.activities.ICategory;
import org.eclipse.ui.activities.IMutableActivityManager;
import org.eclipse.ui.activities.NotDefinedException;
import org.eclipse.ui.internal.activities.InternalActivityHelper;

/**
 * A simple control provider that will allow the user to toggle on/off the
 * activities bound to categories.
 *
 * @since 3.0
 */
public class ActivityEnabler {

	private static final int ALL = 2;

	private static final int NONE = 0;

	private static final int SOME = 1;

	private ISelectionChangedListener selectionListener = new ISelectionChangedListener() {

		@Override
		public void selectionChanged(SelectionChangedEvent event) {
			Object element = ((IStructuredSelection) event.getSelection())
					.getFirstElement();
			try {
				if (element instanceof ICategory) {
					descriptionText.setText(((ICategory) element)
							.getDescription());
				} else if (element instanceof IActivity) {
					descriptionText.setText(((IActivity) element)
							.getDescription());
				}
			} catch (NotDefinedException e) {
			}
		}
	};

	/**
	 * Listener that manages the grey/check state of categories.
	 */
	private ICheckStateListener checkListener = new ICheckStateListener() {

		@Override
		public void checkStateChanged(CheckStateChangedEvent event) {
			Set checked = new HashSet(Arrays.asList(dualViewer
					.getCheckedElements()));
			Object element = event.getElement();
			if (element instanceof ICategory) {
				dualViewer.setSubtreeChecked(element, event.getChecked());
				dualViewer.setGrayed(element, false);
				Object categoryActivities[] = provider.getChildren(element);
				for (Object categoryActivitie : categoryActivities) {
					handleDuplicateActivities(event.getChecked(),
							categoryActivitie);
				}

			} else {
				handleActivityCheck(checked, element);
				handleDuplicateActivities(event.getChecked(), element);
			}
		}

		/**
		 * Handle duplicate activities.
		 *
		 * @param checkedState
		 *            Checked state of the element.
		 * @param element
		 *            The checked element.
		 */
		private void handleDuplicateActivities(boolean checkedState,
				Object element) {
			Object[] duplicateActivities = provider
					.getDuplicateCategoryActivities((CategorizedActivity) element);
			CategorizedActivity activity = null;
			for (Object duplicateActivitie : duplicateActivities) {
				activity = (CategorizedActivity) duplicateActivitie;
				dualViewer.setChecked(activity, checkedState);
				handleActivityCheck(new HashSet(Arrays.asList(dualViewer
						.getCheckedElements())), activity);
			}
		}

		/**
		 * Handle the checking of an activity and update its category's checked
		 * state.
		 *
		 * @param checked
		 *            The set of checked elements in the viewer.
		 * @param element
		 *            The checked element.
		 */
		private void handleActivityCheck(Set checked, Object element) {
			CategorizedActivity proxy = (CategorizedActivity) element;
			Object[] children = provider.getChildren(proxy.getCategory());
			int state = NONE;
			int count = 0;
			for (Object child : children) {
				if (checked.contains(child)) {
					count++;
				}
			}

			if (count == children.length) {
				state = ALL;
			} else if (count != 0) {
				state = SOME;
			}

			if (state == NONE) {
				checked.remove(proxy.getCategory());
			} else {
				checked.add(proxy.getCategory());
			}

			dualViewer.setGrayed(proxy.getCategory(), state == SOME);
			dualViewer.setCheckedElements(checked.toArray());
			handleRequiredActivities(checked, element);
		}

		/**
		 * Handle the activity's required activities (parent and child).
		 *
		 * @param checked
		 *            The set of checked elements in the viewer.
		 * @param element
		 *            The checked element.
		 *
		 */
		private void handleRequiredActivities(Set checked, Object element) {
			Object[] requiredActivities = null;
			if (checked.contains(element)) {
				requiredActivities = provider
						.getChildRequiredActivities(((CategorizedActivity) element)
								.getId());
				for (int index = 0; index < requiredActivities.length; index++) {
					if (!checked.contains(requiredActivities[index])) {
						dualViewer.setChecked(requiredActivities[index], true);
						handleActivityCheck(new HashSet(Arrays
								.asList(dualViewer.getCheckedElements())),
								requiredActivities[index]);
					}
				}
			}
			else {
				requiredActivities = provider
						.getParentRequiredActivities(((CategorizedActivity) element)
								.getId());
				for (Object requiredActivitie : requiredActivities) {
					if (checked.contains(requiredActivitie)) {
						dualViewer.setChecked(requiredActivitie, false);
						handleActivityCheck(new HashSet(Arrays
								.asList(dualViewer.getCheckedElements())),
								requiredActivitie);
					}
				}
			}
		}

	};

	protected CheckboxTreeViewer dualViewer;

	/**
	 * The Set of activities that belong to at least one category.
	 */
	private Set managedActivities = new HashSet(7);

	/**
	 * The content provider.
	 */
	protected ActivityCategoryContentProvider provider = new ActivityCategoryContentProvider();

	/**
	 * The descriptive text.
	 */
	protected Text descriptionText;

	private Properties strings;

    private IMutableActivityManager activitySupport;

	private TableViewer dependantViewer;

	/**
	 * Create a new instance.
	 *
	 * @param activitySupport
	 *            the <code>IMutableActivityMananger</code> to use.
	 * @param strings
	 *            map of strings to use. See the constants on
	 *            {@link org.eclipse.ui.activities.ActivitiesPreferencePage} for
	 *            details.
	 */
	public ActivityEnabler(IMutableActivityManager activitySupport, Properties strings) {
		this.activitySupport = activitySupport;
		this.strings = strings;
	}

	/**
	 * Create the controls.
	 *
	 * @param parent
	 *            the parent in which to create the controls.
	 * @return the composite in which the controls exist.
	 */
	public Control createControl(Composite parent) {
        GC gc = new GC(parent);
        gc.setFont(JFaceResources.getDialogFont());
        FontMetrics fontMetrics = gc.getFontMetrics();
        gc.dispose();

		Composite composite = new Composite(parent, SWT.NONE);

		composite.setLayout(new GridLayout(2, true));

		new Label(composite, SWT.NONE).setText(strings.getProperty(ActivitiesPreferencePage.ACTIVITY_NAME,
				ActivityMessages.ActivityEnabler_activities));
		new Label(composite, SWT.NONE).setText(ActivityMessages.ActivityEnabler_description);

		dualViewer = new CheckboxTreeViewer(composite);
		dualViewer.setComparator(new ViewerComparator());
		dualViewer.setLabelProvider(new ActivityCategoryLabelProvider());
		dualViewer.setContentProvider(provider);
		dualViewer.setInput(activitySupport);
		GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
		data.heightHint = 200;
		dualViewer.getControl().setLayoutData(data);

		Composite detailsComp = new Composite(composite, SWT.NONE);
		detailsComp.setLayout(new GridLayout());
		detailsComp.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		descriptionText = new Text(detailsComp, SWT.READ_ONLY | SWT.WRAP | SWT.BORDER | SWT.V_SCROLL);
		data = new GridData(SWT.FILL, SWT.FILL, true, false);
		data.heightHint = Dialog.convertHeightInCharsToPixels(fontMetrics, 5);
		descriptionText.setLayoutData(data);
		setInitialStates();

		new Label(detailsComp, SWT.NONE).setText(ActivityMessages.ActivitiesPreferencePage_requirements);
		dependantViewer = new TableViewer(detailsComp, SWT.BORDER);
		dependantViewer.getControl().setLayoutData(new GridData(GridData.FILL_BOTH));
		dependantViewer.setContentProvider(new ActivityCategoryContentProvider());
		dependantViewer.setLabelProvider(new ActivityCategoryLabelProvider());
		dependantViewer.setInput(Collections.EMPTY_SET);
		Composite buttonComposite = new Composite(composite, SWT.NONE);
		buttonComposite.setLayout(createGridLayoutWithoutMargins(2, fontMetrics));

		Button selectAllButton = new Button(buttonComposite, SWT.PUSH);
		selectAllButton.setText(ActivityMessages.ActivityEnabler_selectAll);
		selectAllButton.addSelectionListener(widgetSelectedAdapter(e -> toggleTreeEnablement(true)));
		setButtonLayoutData(selectAllButton, fontMetrics);

		Button deselectAllButton = new Button(buttonComposite, SWT.PUSH);
		deselectAllButton.setText(ActivityMessages.ActivityEnabler_deselectAll);
		deselectAllButton.addSelectionListener(widgetSelectedAdapter(e -> toggleTreeEnablement(false)));
		setButtonLayoutData(deselectAllButton, fontMetrics);


		dualViewer.addCheckStateListener(checkListener);
		dualViewer.addSelectionChangedListener(selectionListener);

		dualViewer.setSelection(new StructuredSelection());

        Dialog.applyDialogFont(composite);

		return composite;
	}

	private GridLayout createGridLayoutWithoutMargins(int nColumns, FontMetrics fontMetrics) {
		GridLayout layout = new GridLayout(nColumns, false);
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		layout.horizontalSpacing = Dialog.convertHorizontalDLUsToPixels(fontMetrics, IDialogConstants.HORIZONTAL_SPACING);
		layout.verticalSpacing = Dialog.convertVerticalDLUsToPixels(fontMetrics, IDialogConstants.VERTICAL_SPACING);
		return layout;
	}

    private GridData setButtonLayoutData(Button button, FontMetrics fontMetrics) {
        GridData data = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
        int widthHint = Dialog.convertHorizontalDLUsToPixels(fontMetrics, IDialogConstants.BUTTON_WIDTH);
        Point minSize = button.computeSize(SWT.DEFAULT, SWT.DEFAULT, true);
        data.widthHint = Math.max(widthHint, minSize.x);
        button.setLayoutData(data);
        return data;
    }

	/**
	 * Set the enabled category/activity check/grey states based on initial
	 * activity enablement.
	 */
	private void setInitialStates() {
		Set enabledActivities = activitySupport
				.getEnabledActivityIds();
		setEnabledStates(enabledActivities);
	}

	private void setEnabledStates(Set enabledActivities) {
		Set categories = activitySupport
				.getDefinedCategoryIds();
		List checked = new ArrayList(10), grayed = new ArrayList(10);
		for (Iterator i = categories.iterator(); i.hasNext();) {
			String categoryId = (String) i.next();
			ICategory category = activitySupport
					.getCategory(categoryId);

			int state = NONE;

			Collection activities = InternalActivityHelper
					.getActivityIdsForCategory(activitySupport, category);
			int foundCount = 0;
			for (Iterator j = activities.iterator(); j.hasNext();) {
				String activityId = (String) j.next();
				managedActivities.add(activityId);
				if (enabledActivities.contains(activityId)) {
					IActivity activity = activitySupport
							.getActivity(activityId);
					checked.add(new CategorizedActivity(category, activity));
					foundCount++;
				}
			}

			if (foundCount == activities.size()) {
				state = ALL;
			} else if (foundCount > 0) {
				state = SOME;
			}

			if (state == NONE) {
				continue;
			}
			checked.add(category);

			if (state == SOME) {
				grayed.add(category);
			}
		}

		dualViewer.setCheckedElements(checked.toArray());
		dualViewer.setGrayedElements(grayed.toArray());
	}

	/**
	 * Update activity enablement based on the check states of activities in the
	 * tree.
	 */
	public void updateActivityStates() {
		Set enabledActivities = new HashSet(activitySupport
                .getEnabledActivityIds());

		enabledActivities.removeAll(managedActivities);

		Object[] checked = dualViewer.getCheckedElements();
		for (Object element : checked) {
			if (element instanceof ICategory || dualViewer.getGrayed(element)) {
				continue;
			}
			enabledActivities.add(((IActivity) element).getId());
		}

		activitySupport.setEnabledActivityIds(enabledActivities);
	}

	/**
	 * Restore the default activity states.
	 */
	public void restoreDefaults() {
	    Set defaultEnabled = new HashSet();
	    Set activityIds = activitySupport.getDefinedActivityIds();
	    for (Iterator i = activityIds.iterator(); i.hasNext();) {
            String activityId = (String) i.next();
            IActivity activity = activitySupport.getActivity(activityId);
            try {
                if (activity.isDefaultEnabled()) {
                    defaultEnabled.add(activityId);
                }
            } catch (NotDefinedException e) {
            }
        }

		setEnabledStates(defaultEnabled);
	}

	/**
	 * Toggles the enablement state of all activities.
	 *
	 * @param enabled
	 *            whether the tree should be enabled
	 */
	protected void toggleTreeEnablement(boolean enabled) {
		Object[] elements = provider.getElements(activitySupport);

		dualViewer.setGrayedElements(new Object[0]);

		for (Object element : elements) {
			dualViewer
					.expandToLevel(element, AbstractTreeViewer.ALL_LEVELS);
			dualViewer.setSubtreeChecked(element, enabled);
		}
	}
}

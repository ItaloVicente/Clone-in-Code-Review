package org.eclipse.ui.internal.activities.ws;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
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
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
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
				descriptionText.setText(""); //$NON-NLS-1$
			}
		}
	};

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
				for (int index = 0; index < categoryActivities.length; index++) {
					handleDuplicateActivities(event.getChecked(),
							categoryActivities[index]);
				}

			} else {
				handleActivityCheck(checked, element);
				handleDuplicateActivities(event.getChecked(), element);
			}
		}

		private void handleDuplicateActivities(boolean checkedState,
				Object element) {
			Object[] duplicateActivities = provider
					.getDuplicateCategoryActivities((CategorizedActivity) element);
			CategorizedActivity activity = null;
			for (int index = 0; index < duplicateActivities.length; index++) {
				activity = (CategorizedActivity) duplicateActivities[index];
				dualViewer.setChecked(activity, checkedState);
				handleActivityCheck(new HashSet(Arrays.asList(dualViewer
						.getCheckedElements())), activity);
			}
		}

		private void handleActivityCheck(Set checked, Object element) {
			CategorizedActivity proxy = (CategorizedActivity) element;
			Object[] children = provider.getChildren(proxy.getCategory());
			int state = NONE;
			int count = 0;
			for (int i = 0; i < children.length; i++) {
				if (checked.contains(children[i])) {
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
				for (int index = 0; index < requiredActivities.length; index++) {
					if (checked.contains(requiredActivities[index])) {
						dualViewer.setChecked(requiredActivities[index], false);
						handleActivityCheck(new HashSet(Arrays
								.asList(dualViewer.getCheckedElements())),
								requiredActivities[index]);
					}
				}
			}
		}

	};

	protected CheckboxTreeViewer dualViewer;

	private Set managedActivities = new HashSet(7);

	protected ActivityCategoryContentProvider provider = new ActivityCategoryContentProvider();

	protected Text descriptionText;

	private Properties strings;

    private IMutableActivityManager activitySupport;

	public ActivityEnabler(IMutableActivityManager activitySupport, Properties strings) {
		this.activitySupport = activitySupport;
		this.strings = strings;
	}

	public Control createControl(Composite parent) {
        GC gc = new GC(parent);
        gc.setFont(JFaceResources.getDialogFont());
        FontMetrics fontMetrics = gc.getFontMetrics();
        gc.dispose();
        
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(createGridLayoutWithoutMargins(1, fontMetrics));

		new Label(composite, SWT.NONE).setText(strings.getProperty(ActivitiesPreferencePage.ACTIVITY_NAME, ActivityMessages.ActivityEnabler_activities) + ':');

		dualViewer = new CheckboxTreeViewer(composite);
		dualViewer.setComparator(new ViewerComparator());
		dualViewer.setLabelProvider(new ActivityCategoryLabelProvider());
		dualViewer.setContentProvider(provider);
		dualViewer.setInput(activitySupport);
		GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
		dualViewer.getControl().setLayoutData(data);

		Composite buttonComposite = new Composite(composite, SWT.NONE);
		buttonComposite.setLayout(createGridLayoutWithoutMargins(2, fontMetrics));

		Button selectAllButton = new Button(buttonComposite, SWT.PUSH);
		selectAllButton.setText(ActivityMessages.ActivityEnabler_selectAll);
		selectAllButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				toggleTreeEnablement(true);
			}
		});
		setButtonLayoutData(selectAllButton, fontMetrics);

		Button deselectAllButton = new Button(buttonComposite, SWT.PUSH);
		deselectAllButton.setText(ActivityMessages.ActivityEnabler_deselectAll); 
		deselectAllButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				toggleTreeEnablement(false);
			}
		});
		setButtonLayoutData(deselectAllButton, fontMetrics);

		new Label(composite, SWT.NONE).setText(ActivityMessages.ActivityEnabler_description);

		descriptionText = new Text(composite, SWT.READ_ONLY | SWT.WRAP | SWT.BORDER
				| SWT.V_SCROLL);
		data = new GridData(SWT.FILL, SWT.FILL, true, false);
		data.heightHint = Dialog.convertHeightInCharsToPixels(fontMetrics, 5);
		descriptionText.setLayoutData(data);
		setInitialStates();

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

	public void updateActivityStates() {
		Set enabledActivities = new HashSet(activitySupport
                .getEnabledActivityIds());

		enabledActivities.removeAll(managedActivities);

		Object[] checked = dualViewer.getCheckedElements();
		for (int i = 0; i < checked.length; i++) {
			Object element = checked[i];
			if (element instanceof ICategory || dualViewer.getGrayed(element)) {
				continue;
			}
			enabledActivities.add(((IActivity) element).getId());
		}

		activitySupport.setEnabledActivityIds(enabledActivities);
	}

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

	protected void toggleTreeEnablement(boolean enabled) {
		Object[] elements = provider.getElements(activitySupport);
		
		dualViewer.setGrayedElements(new Object[0]);
		
		for (int i = 0; i < elements.length; i++) {
			dualViewer
					.expandToLevel(elements[i], AbstractTreeViewer.ALL_LEVELS);
			dualViewer.setSubtreeChecked(elements[i], enabled);
		}
	}
}

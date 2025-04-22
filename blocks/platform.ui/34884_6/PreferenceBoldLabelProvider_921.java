
package org.eclipse.ui.internal.dialogs;

import com.ibm.icu.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.advanced.MPerspective;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.ui.IPerspectiveDescriptor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPreferenceConstants;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.IPreferenceConstants;
import org.eclipse.ui.internal.IWorkbenchHelpContextIds;
import org.eclipse.ui.internal.Workbench;
import org.eclipse.ui.internal.WorkbenchMessages;
import org.eclipse.ui.internal.WorkbenchPlugin;
import org.eclipse.ui.internal.WorkbenchWindow;
import org.eclipse.ui.internal.registry.PerspectiveDescriptor;
import org.eclipse.ui.internal.registry.PerspectiveRegistry;
import org.eclipse.ui.internal.util.Descriptors;
import org.eclipse.ui.internal.util.PrefUtil;
import org.eclipse.ui.internal.util.Util;

public class PerspectivesPreferencePage extends PreferencePage implements
		IWorkbenchPreferencePage {
	private IWorkbench workbench;

	private PerspectiveRegistry perspectiveRegistry;

	private ArrayList<IPerspectiveDescriptor> perspectives;

	private String defaultPerspectiveId;

	private ArrayList<IPerspectiveDescriptor> perspToDelete = new ArrayList<IPerspectiveDescriptor>();

	private ArrayList<IPerspectiveDescriptor> perspToRevert = new ArrayList<IPerspectiveDescriptor>();

	private Table perspectivesTable;
	
	private Button revertButton;

	private Button deleteButton;

	private Button setDefaultButton;

	private Label openViewModeLabel;

	private Button openSameWindowButton;

	private Button openNewWindowButton;

	private int openPerspMode;

	private int openViewMode;

	private Button openEmbedButton;

	private Button openFastButton;

	private Button fvbHideButton;
	
	private boolean isFVBConfigured;
    
	private final String FVG_TITLE = WorkbenchMessages.FastViewsGroup_title;

	private final String OVM_TITLE = WorkbenchMessages.OpenViewMode_title;

	private final String OVM_EMBED = WorkbenchMessages.OpenViewMode_embed;

	private final String OVM_FAST = WorkbenchMessages.OpenViewMode_fast; 

	private final String FVB_HIDE = WorkbenchMessages.FastViewBar_hide;

	private final String OPM_TITLE = WorkbenchMessages.OpenPerspectiveMode_optionsTitle; 

	private final String OPM_SAME_WINDOW = WorkbenchMessages.OpenPerspectiveMode_sameWindow; 

	private final String OPM_NEW_WINDOW = WorkbenchMessages.OpenPerspectiveMode_newWindow; 

	private Comparator<IPerspectiveDescriptor> comparator = new Comparator<IPerspectiveDescriptor>() {
        private Collator collator = Collator.getInstance();

		@Override
		public int compare(IPerspectiveDescriptor ob1, IPerspectiveDescriptor ob2) {
			IPerspectiveDescriptor d1 = ob1;
			IPerspectiveDescriptor d2 = ob2;
            return collator.compare(d1.getLabel(), d2.getLabel());
        }
    };

	@Override
	protected Control createContents(Composite parent) {
		PlatformUI.getWorkbench().getHelpSystem().setHelp(parent,
				IWorkbenchHelpContextIds.PERSPECTIVES_PREFERENCE_PAGE);

		Composite composite = createComposite(parent);

		createOpenPerspButtonGroup(composite);
		createOpenViewButtonGroup(composite);
		createCustomizePerspective(composite);

		return composite;
	}

	protected Composite createComposite(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		GridData data = new GridData(GridData.FILL_BOTH);
		composite.setLayoutData(data);
		composite.setFont(parent.getFont());
		GridLayout layout = new GridLayout();
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		layout.verticalSpacing = 10;
		composite.setLayout(layout);
		return composite;
	}

	protected void createOpenPerspButtonGroup(Composite composite) {

		Font font = composite.getFont();

		Group buttonComposite = new Group(composite, SWT.LEFT);
		buttonComposite.setText(OPM_TITLE);
		buttonComposite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		buttonComposite.setFont(composite.getFont());
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		buttonComposite.setLayout(layout);

		openSameWindowButton = new Button(buttonComposite, SWT.RADIO);
		openSameWindowButton.setText(OPM_SAME_WINDOW);
		openSameWindowButton
				.setSelection(IPreferenceConstants.OPM_ACTIVE_PAGE == openPerspMode);
		openSameWindowButton.setFont(font);
		openSameWindowButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				openPerspMode = IPreferenceConstants.OPM_ACTIVE_PAGE;
			}
		});

		openNewWindowButton = new Button(buttonComposite, SWT.RADIO);
		openNewWindowButton.setText(OPM_NEW_WINDOW);
		openNewWindowButton
				.setSelection(IPreferenceConstants.OPM_NEW_WINDOW == openPerspMode);
		openNewWindowButton.setFont(font);
		openNewWindowButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				openPerspMode = IPreferenceConstants.OPM_NEW_WINDOW;
			}
		});

	}

	protected void createOpenViewButtonGroup(Composite composite) {

		Font font = composite.getFont();

		Group buttonComposite = new Group(composite, SWT.LEFT);
		buttonComposite.setText(FVG_TITLE);
		buttonComposite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		buttonComposite.setFont(composite.getFont());
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		buttonComposite.setLayout(layout);

		openViewModeLabel = new Label(buttonComposite, SWT.NONE);
		openViewModeLabel.setText(OVM_TITLE);
		GridData data = new GridData();
		data.horizontalSpan = 2;
		openViewModeLabel.setLayoutData(data);

		openEmbedButton = new Button(buttonComposite, SWT.RADIO);
		openEmbedButton.setText(OVM_EMBED);
		openEmbedButton
				.setSelection(openViewMode == IPreferenceConstants.OVM_EMBED);
		openEmbedButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				openViewMode = IPreferenceConstants.OVM_EMBED;
			}
		});
		openEmbedButton.setFont(font);

		if (openViewMode == IPreferenceConstants.OVM_FLOAT) {
			openViewMode = IPreferenceConstants.OVM_FAST;
		}

		openFastButton = new Button(buttonComposite, SWT.RADIO);
		openFastButton.setText(OVM_FAST);
		openFastButton
				.setSelection(openViewMode == IPreferenceConstants.OVM_FAST);
		openFastButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				openViewMode = IPreferenceConstants.OVM_FAST;
			}
		});
		openFastButton.setFont(font);

		createFVBHideButton(buttonComposite);
	}

	protected void createFVBHideButton(Composite composite) {
		if (!isFVBConfigured)
			return;
		Font font = composite.getFont();
		fvbHideButton = new Button(composite, SWT.CHECK);
		GridData data = new GridData();
		data.horizontalSpan = 2;
		fvbHideButton.setLayoutData(data);
		fvbHideButton.setText(FVB_HIDE);

		fvbHideButton.setSelection(this.getPreferenceStore().getBoolean(
				IPreferenceConstants.FVB_HIDE));
		fvbHideButton.setFont(font);
	}

	protected Composite createCustomizePerspective(Composite parent) {

		Font font = parent.getFont();

		Composite perspectivesComponent = new Composite(parent, SWT.NONE);
		perspectivesComponent.setLayoutData(new GridData(GridData.FILL_BOTH));
		perspectivesComponent.setFont(parent.getFont());

		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		perspectivesComponent.setLayout(layout);

		Label label = new Label(perspectivesComponent, SWT.LEFT);
		label.setText(WorkbenchMessages.PerspectivesPreference_available); 
		GridData data = new GridData();
		data.horizontalSpan = 2;
		label.setLayoutData(data);
		label.setFont(font);

		perspectivesTable = new Table(perspectivesComponent, SWT.H_SCROLL | SWT.V_SCROLL
				| SWT.BORDER);
	    perspectivesTable.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				updateButtons();
			}
		});
        perspectivesTable.setFont(font);

		data = new GridData(GridData.FILL_BOTH);
		data.grabExcessHorizontalSpace = true;
		data.grabExcessVerticalSpace = true;
		perspectivesTable.setLayoutData(data);

		IPerspectiveDescriptor[] persps = perspectiveRegistry.getPerspectives();
		perspectives = new ArrayList<IPerspectiveDescriptor>(persps.length);
		for (int i = 0; i < persps.length; i++) {
			perspectives.add(i, persps[i]);
		}
		Collections.sort(perspectives, comparator);
		defaultPerspectiveId = perspectiveRegistry.getDefaultPerspective();
		updatePerspectivesTable();
		
		Composite buttonBar = (Composite) createVerticalButtonBar(perspectivesComponent);
		data = new GridData(GridData.FILL_VERTICAL);
		buttonBar.setLayoutData(data);
		
		String NOTE_LABEL = WorkbenchMessages.Preference_note;
		String REVERT_NOTE = WorkbenchMessages.RevertPerspective_note; 
		Composite noteComposite = createNoteComposite(font, parent,
                NOTE_LABEL, REVERT_NOTE);
        GridData noteData = new GridData();
        noteData.horizontalSpan = 2;
        noteComposite.setLayoutData(noteData);
		return perspectivesComponent;
	}

	protected Button createVerticalButton(Composite parent, String label,
			boolean defaultButton) {
		Button button = new Button(parent, SWT.PUSH);

		button.setText(label);

		GridData data = setButtonLayoutData(button);
		data.horizontalAlignment = GridData.FILL;

		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				verticalButtonPressed(event.widget);
			}
		});
		button.setToolTipText(label);
		if (defaultButton) {
			Shell shell = parent.getShell();
			if (shell != null) {
				shell.setDefaultButton(button);
			}
		}
		button.setFont(parent.getFont());
		return button;
	}

	protected Control createVerticalButtonBar(Composite parent) {
		Composite composite = new Composite(parent, SWT.NULL);

		GridLayout layout = new GridLayout();
		layout.numColumns = 1;
		layout.marginWidth = 5;
		layout.marginHeight = 0;
		layout.horizontalSpacing = convertHorizontalDLUsToPixels(IDialogConstants.HORIZONTAL_SPACING);
		layout.verticalSpacing = convertVerticalDLUsToPixels(IDialogConstants.VERTICAL_SPACING);
		composite.setLayout(layout);
		composite.setFont(parent.getFont());

		setDefaultButton = createVerticalButton(composite, WorkbenchMessages.PerspectivesPreference_MakeDefault, false); 
		setDefaultButton.setToolTipText(WorkbenchMessages.PerspectivesPreference_MakeDefaultTip); 

		revertButton = createVerticalButton(composite, WorkbenchMessages.PerspectivesPreference_Reset, false); 
		revertButton.setToolTipText(WorkbenchMessages.PerspectivesPreference_ResetTip); 

		deleteButton = createVerticalButton(composite, WorkbenchMessages.PerspectivesPreference_Delete, false); 
		deleteButton.setToolTipText(WorkbenchMessages.PerspectivesPreference_DeleteTip); 
		updateButtons();

		return composite;
	}

	@Override
	public void init(IWorkbench aWorkbench) {
		this.workbench = aWorkbench;
		this.perspectiveRegistry = (PerspectiveRegistry) workbench
				.getPerspectiveRegistry();
		IPreferenceStore store = WorkbenchPlugin.getDefault()
				.getPreferenceStore();
		setPreferenceStore(store);

		openViewMode = store.getInt(IPreferenceConstants.OPEN_VIEW_MODE);
		openPerspMode = store.getInt(IPreferenceConstants.OPEN_PERSP_MODE);
		isFVBConfigured = ((WorkbenchWindow) workbench
				.getActiveWorkbenchWindow()).getShowFastViewBars();

	}

	@Override
	protected void performDefaults() {
		IPreferenceStore store = WorkbenchPlugin.getDefault()
				.getPreferenceStore();

		openViewMode = store.getDefaultInt(IPreferenceConstants.OPEN_VIEW_MODE);
		if (openViewMode == IPreferenceConstants.OVM_FLOAT) {
			openViewMode = IPreferenceConstants.OVM_FAST;
		}
		openEmbedButton
				.setSelection(openViewMode == IPreferenceConstants.OVM_EMBED);
		openFastButton
				.setSelection(openViewMode == IPreferenceConstants.OVM_FAST);

		if (isFVBConfigured)
			fvbHideButton.setSelection(store
					.getDefaultBoolean(IPreferenceConstants.FVB_HIDE));

		openPerspMode = store
				.getDefaultInt(IPreferenceConstants.OPEN_PERSP_MODE);
		openSameWindowButton
				.setSelection(IPreferenceConstants.OPM_ACTIVE_PAGE == openPerspMode);
		openNewWindowButton
				.setSelection(IPreferenceConstants.OPM_NEW_WINDOW == openPerspMode);

		String currentDefault = perspectiveRegistry.getDefaultPerspective();
		
		int index = indexOf(currentDefault);
		if (index >= 0){
			defaultPerspectiveId = currentDefault;
			updatePerspectivesTable();
			perspectivesTable.setSelection(index);			
		}
		
		String newDefault = PrefUtil.getAPIPreferenceStore().getDefaultString(
                IWorkbenchPreferenceConstants.DEFAULT_PERSPECTIVE_ID);
		
		IPerspectiveDescriptor desc = null;
        if (newDefault != null) {
			desc = workbench.getPerspectiveRegistry().findPerspectiveWithId(newDefault);
		}
        if (desc == null) {
        	newDefault = workbench.getPerspectiveRegistry().getDefaultPerspective();
        }
        
        defaultPerspectiveId = newDefault;
        updatePerspectivesTable();

	}

	private int indexOf(String perspectiveId) {
		if (perspectiveId == null) {
			return -1;
		}
		PerspectiveDescriptor[] descriptors =
			new PerspectiveDescriptor[perspectives.size()];
		perspectives.toArray(descriptors);
		for (int i = 0; i < descriptors.length; i++) {
			PerspectiveDescriptor descriptor = descriptors[i];
			if(descriptor.getId().equals(perspectiveId)) {
				return i;
			}
		}
		return -1;
	}

	private boolean canDeletePerspective(IPerspectiveDescriptor desc) {

		MApplication application = ((Workbench) workbench).getApplication();
		EModelService modelService = application.getContext().get(EModelService.class);
		
		if (modelService.findElements(application, desc.getId(), MPerspective.class, null)
				.isEmpty())
			return true;

		return MessageDialog.openQuestion(
				getShell(),
				WorkbenchMessages.PerspectivesPreference_perspectiveopen_title,
				NLS.bind(WorkbenchMessages.PerspectivesPreference_perspectiveopen_message,
						desc.getLabel()));
	}

	@Override
	public boolean performOk() {
		if (!Util.equals(defaultPerspectiveId, perspectiveRegistry.getDefaultPerspective())) {
			perspectiveRegistry.setDefaultPerspective(defaultPerspectiveId);
		}

		
		for (IPerspectiveDescriptor perspective : perspToDelete) {
			IWorkbenchWindow[] windows = workbench.getWorkbenchWindows();
			for (IWorkbenchWindow window : windows) {
				IWorkbenchPage[] pages = window.getPages();
				for (IWorkbenchPage page : pages) {
					page.closePerspective(perspective, true, false);
				}
			}
			perspectiveRegistry.deletePerspectives(perspToDelete);
		}

		for (IPerspectiveDescriptor perspective : perspToRevert) {
			perspectiveRegistry.revertPerspective(perspective);
		}

		IPreferenceStore store = getPreferenceStore();

		store.setValue(IPreferenceConstants.OPEN_VIEW_MODE, openViewMode);

		if (isFVBConfigured) {
			store.setValue(IPreferenceConstants.FVB_HIDE, fvbHideButton
					.getSelection());
		}

		store.setValue(IPreferenceConstants.OPEN_PERSP_MODE, openPerspMode);

		PrefUtil.savePrefs();

		return true;
	}

	protected void updateButtons() {
		int index = perspectivesTable.getSelectionIndex();

		PerspectiveDescriptor desc = null;
		if (index > -1) {
			desc = (PerspectiveDescriptor) perspectives.get(index);
		}

		if (desc != null) {
			revertButton.setEnabled(desc.isPredefined() && desc.hasCustomDefinition()
					&& !perspToRevert.contains(desc));
			deleteButton.setEnabled(!desc.isPredefined());
			setDefaultButton.setEnabled(true);
		} else {
			revertButton.setEnabled(false);
			deleteButton.setEnabled(false);
			setDefaultButton.setEnabled(false);
		}
	}

	protected void updatePerspectivesTable() {
		perspectivesTable.removeAll();
		for (int i = 0; i < perspectives.size(); i++) {
        	PerspectiveDescriptor persp = (PerspectiveDescriptor) perspectives.get(i);
        	newPerspectivesTableItem(persp, i, false);
        }
    }
	
	protected TableItem newPerspectivesTableItem(IPerspectiveDescriptor persp,
            int index, boolean selected) {
        
        ImageDescriptor image = persp.getImageDescriptor();
        
        TableItem item = new TableItem(perspectivesTable, SWT.NULL, index);
        if (image != null) {
            Descriptors.setImage(item, image);
        }
        String label=persp.getLabel();
        if (persp.getId().equals(defaultPerspectiveId)){
			label = NLS.bind(WorkbenchMessages.PerspectivesPreference_defaultLabel, label ); 
    	    
		}
        item.setText(label);
        item.setData(persp);
        if (selected) {
        	perspectivesTable.setSelection(index);
        }

        return item;
    }

	protected void verticalButtonPressed(Widget button) {
		int index = perspectivesTable.getSelectionIndex();

		PerspectiveDescriptor desc = null;
		if (index > -1) {
			desc = (PerspectiveDescriptor) perspectives.get(index);
		} else {
			return;
		}

		if (button == revertButton) {
			if (!perspToRevert.contains(desc)) {
				perspToRevert.add(desc);
			}
		} else if (button == deleteButton) {
			if (!perspToDelete.contains(desc)) {
				if (canDeletePerspective(desc)) {
					perspToDelete.add(desc);
					perspToRevert.remove(desc);
					perspectives.remove(desc);				
					updatePerspectivesTable();
				}
					
			}
		} else if (button == setDefaultButton) {
			defaultPerspectiveId = desc.getId();
			updatePerspectivesTable();
			perspectivesTable.setSelection(index);
		}

		updateButtons();
	}
}

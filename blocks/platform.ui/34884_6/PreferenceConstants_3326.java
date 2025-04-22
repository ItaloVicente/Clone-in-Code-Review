package org.eclipse.ui.examples.fieldassist.preferences;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.preference.*;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.examples.fieldassist.FieldAssistPlugin;
import org.eclipse.ui.examples.fieldassist.TaskAssistExampleMessages;


public class FieldAssistPreferencePage extends FieldEditorPreferencePage
		implements IWorkbenchPreferencePage {

	public FieldAssistPreferencePage() {
		super(GRID);
		setPreferenceStore(FieldAssistPlugin.getDefault().getPreferenceStore());
		setDescription(TaskAssistExampleMessages.Preferences_Description);
	}

	public void createFieldEditors() {
		addField(new RadioGroupFieldEditor(
				PreferenceConstants.PREF_DECORATOR_VERTICALLOCATION,
				TaskAssistExampleMessages.Preferences_DecoratorVert,
				1,
				new String[][] {
						{
								TaskAssistExampleMessages.Preferences_DecoratorTop,
								PreferenceConstants.PREF_DECORATOR_VERTICALLOCATION_TOP },
						{
								TaskAssistExampleMessages.Preferences_DecoratorCenter,
								PreferenceConstants.PREF_DECORATOR_VERTICALLOCATION_CENTER },
						{
								TaskAssistExampleMessages.Preferences_DecoratorBottom,
								PreferenceConstants.PREF_DECORATOR_VERTICALLOCATION_BOTTOM } },
				getFieldEditorParent()));

		addField(new RadioGroupFieldEditor(
				PreferenceConstants.PREF_DECORATOR_HORIZONTALLOCATION,
				TaskAssistExampleMessages.Preferences_DecoratorHorz,
				1,
				new String[][] {
						{
								TaskAssistExampleMessages.Preferences_DecoratorLeft,
								PreferenceConstants.PREF_DECORATOR_HORIZONTALLOCATION_LEFT },
						{
								TaskAssistExampleMessages.Preferences_DecoratorRight,
								PreferenceConstants.PREF_DECORATOR_HORIZONTALLOCATION_RIGHT } },
				getFieldEditorParent()));

		IntegerFieldEditor editor = new IntegerFieldEditor(
				PreferenceConstants.PREF_DECORATOR_MARGINWIDTH,
				TaskAssistExampleMessages.Preferences_DecoratorMargin,
				getFieldEditorParent());
		editor.setValidRange(0, 10);
		addField(editor);

		Label label = new Label(getFieldEditorParent(), SWT.WRAP);
		label.setText(TaskAssistExampleMessages.Preferences_ErrorIndicator);
		addField(new BooleanFieldEditor(
				PreferenceConstants.PREF_SHOWERRORMESSAGE,
				TaskAssistExampleMessages.Preferences_ShowErrorMessage,
				getFieldEditorParent()));

		addField(new BooleanFieldEditor(
				PreferenceConstants.PREF_SHOWERRORDECORATION,
				TaskAssistExampleMessages.Preferences_ShowErrorDecorator,
				getFieldEditorParent()));

		label = new Label(getFieldEditorParent(), SWT.WRAP);
		label
				.setText(TaskAssistExampleMessages.Preferences_RequiredFieldIndicator);
		addField(new BooleanFieldEditor(
				PreferenceConstants.PREF_SHOWREQUIREDFIELDLABELINDICATOR,
				TaskAssistExampleMessages.Preferences_ShowRequiredFieldLabelIndicator,
				getFieldEditorParent()));

		addField(new BooleanFieldEditor(
				PreferenceConstants.PREF_SHOWREQUIREDFIELDDECORATION,
				TaskAssistExampleMessages.Preferences_ShowRequiredFieldDecorator,
				getFieldEditorParent()));

		label = new Label(getFieldEditorParent(), SWT.WRAP);
		label.setText(TaskAssistExampleMessages.Preferences_DecoratorDetails);
		addField(new BooleanFieldEditor(
				PreferenceConstants.PREF_SHOWWARNINGDECORATION,
				TaskAssistExampleMessages.Preferences_ShowWarningDecorator,
				getFieldEditorParent()));
		addField(new BooleanFieldEditor(
				PreferenceConstants.PREF_SHOWCONTENTPROPOSALCUE,
				TaskAssistExampleMessages.Preferences_ShowProposalCue,
				getFieldEditorParent()));

		Dialog.applyDialogFont(getFieldEditorParent());
	}

	public void init(IWorkbench workbench) {
	}

}

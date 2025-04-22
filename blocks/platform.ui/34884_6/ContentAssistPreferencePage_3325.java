package org.eclipse.ui.examples.fieldassist.preferences;

import org.eclipse.jface.preference.*;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.examples.fieldassist.FieldAssistPlugin;
import org.eclipse.ui.examples.fieldassist.TaskAssistExampleMessages;


public class ContentAssistPreferencePage extends FieldEditorPreferencePage
		implements IWorkbenchPreferencePage {

	public ContentAssistPreferencePage() {
		super(GRID);
		setPreferenceStore(FieldAssistPlugin.getDefault().getPreferenceStore());
		setDescription(TaskAssistExampleMessages.Preferences_ContentAssistDescription);
	}

	public void createFieldEditors() {
		addField(new RadioGroupFieldEditor(
				PreferenceConstants.PREF_CONTENTASSISTKEY,
				TaskAssistExampleMessages.Preferences_ContentAssistKey,
				1,
				new String[][] {
						{ PreferenceConstants.PREF_CONTENTASSISTKEY1,
								PreferenceConstants.PREF_CONTENTASSISTKEY1 },
						{ PreferenceConstants.PREF_CONTENTASSISTKEY2,
								PreferenceConstants.PREF_CONTENTASSISTKEY2 },
						{ PreferenceConstants.PREF_CONTENTASSISTKEYAUTO,
								PreferenceConstants.PREF_CONTENTASSISTKEYAUTO },
						{ PreferenceConstants.PREF_CONTENTASSISTKEYAUTOSUBSET,
								PreferenceConstants.PREF_CONTENTASSISTKEYAUTOSUBSET }, },
				getFieldEditorParent()));
		
		IntegerFieldEditor editor = new IntegerFieldEditor(
				PreferenceConstants.PREF_CONTENTASSISTDELAY,
				TaskAssistExampleMessages.Preferences_ContentAssistDelay,
				getFieldEditorParent());
		editor.setValidRange(0, 10000);
		addField(editor);

		addField(new BooleanFieldEditor(
				PreferenceConstants.PREF_CONTENTASSISTKEY_PROPAGATE,
				TaskAssistExampleMessages.Preferences_ContentAssistKeyPropagate,
				getFieldEditorParent()));

		addField(new BooleanFieldEditor(
				PreferenceConstants.PREF_SHOWSECONDARYPOPUP,
				TaskAssistExampleMessages.Preferences_ShowSecondaryPopup,
				getFieldEditorParent()));

		addField(new RadioGroupFieldEditor(
				PreferenceConstants.PREF_CONTENTASSISTRESULT,
				TaskAssistExampleMessages.Preferences_ContentAssistResult,
				1,
				new String[][] {
						{
								TaskAssistExampleMessages.Preferences_ContentAssistResultReplace,
								PreferenceConstants.PREF_CONTENTASSISTRESULT_REPLACE },
						{
								TaskAssistExampleMessages.Preferences_ContentAssistResultInsert,
								PreferenceConstants.PREF_CONTENTASSISTRESULT_INSERT },
						{
								TaskAssistExampleMessages.Preferences_ContentAssistResultNone,
								PreferenceConstants.PREF_CONTENTASSISTRESULT_NONE } },
				getFieldEditorParent()));
		
		addField(new RadioGroupFieldEditor(
				PreferenceConstants.PREF_CONTENTASSISTFILTER,
				TaskAssistExampleMessages.Preferences_ContentAssistFilter,
				1,
				new String[][] {
						{
								TaskAssistExampleMessages.Preferences_ContentAssistFilterCharacter,
								PreferenceConstants.PREF_CONTENTASSISTFILTER_CHAR },
						{
								TaskAssistExampleMessages.Preferences_ContentAssistFilterCumulative,
								PreferenceConstants.PREF_CONTENTASSISTFILTER_CUMULATIVE },
						{
								TaskAssistExampleMessages.Preferences_ContentAssistFilterNone,
								PreferenceConstants.PREF_CONTENTASSISTFILTER_NONE } },
				getFieldEditorParent()));

	}

	public void init(IWorkbench workbench) {
	}

}

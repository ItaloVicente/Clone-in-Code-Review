	public static class ReuseCompareEditorAction extends Action implements
			IPreferenceChangeListener, IWorkbenchAction {
		IEclipsePreferences node = new InstanceScope().getNode(TEAM_UI_PLUGIN);

		public ReuseCompareEditorAction() {
			node.addPreferenceChangeListener(this);
			setText(UIText.GitHistoryPage_ReuseCompareEditorMenuLabel);
			setChecked(CompareUtils.isReuseOpenEditor());
		}

		public void run() {
			CompareUtils.setReuseOpenEditor(isChecked());
		}

		public void dispose() {
			node.removePreferenceChangeListener(this);
		}

		public void preferenceChange(PreferenceChangeEvent event) {
			setChecked(isReuseOpenEditor());

		}
	}


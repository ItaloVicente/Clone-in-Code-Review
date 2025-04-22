					PromptContinueHandler promptContinueHandler = new FileNamePromptContinueHandler(
							changedFilePath);

					@SuppressWarnings("restriction")
					boolean swapSides = CompareUIPlugin.getDefault()
							.getPreferenceStore()
							.getBoolean(ComparePreferencePage.SWAPPED);
					Type typeLocal;
					Type typeRemote;
					if (swapSides) {
						typeLocal = FileElement.Type.REMOTE;
						typeRemote = FileElement.Type.LOCAL;
					} else {
						typeLocal = FileElement.Type.LOCAL;
						typeRemote = FileElement.Type.REMOTE;
					}

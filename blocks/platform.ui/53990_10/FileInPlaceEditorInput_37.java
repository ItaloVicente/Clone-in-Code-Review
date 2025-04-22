                    changeRunnable = () -> {
					    if (embeddedEditor != null) {
					        embeddedEditor.sourceDeleted();
					        embeddedEditor.getSite().getPage().closeEditor(
					                embeddedEditor, true);
					    }
					};

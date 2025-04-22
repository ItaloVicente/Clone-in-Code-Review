			if (editorDesc != null && !editorDesc.isOpenExternal()) {
				result = page.openEditor(new FileEditorInput(file), editorDesc
						.getId(), true);
			} else {
				result = openNonExternalEditor(page, file);
			}

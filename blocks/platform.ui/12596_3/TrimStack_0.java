				Object data = selectedToolItem.getData();
				if (data instanceof MPart) {
					createPartMenu((MPart) data);
				} else if (data instanceof MPerspective) {
					createEmtpyEditorAreaMenu();
				} else if (EMPTY_EDITOR_AREA.equals(data)) {
					createEmtpyEditorAreaMenu();

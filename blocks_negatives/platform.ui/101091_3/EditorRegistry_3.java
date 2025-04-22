				idMementos = childMemento
						.getChildren(IWorkbenchConstants.TAG_DEFAULT_EDITOR);
				String[] defaultEditorIds = new String[idMementos.length];
				for (int j = 0; j < idMementos.length; j++) {
					defaultEditorIds[j] = idMementos[j]
							.getString(IWorkbenchConstants.TAG_ID);

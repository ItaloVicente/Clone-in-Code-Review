		private EditorInputData(String editorId, IEditorInput input) {
			this.editorId = editorId;
			this.input = input;
		}
	}

	private EditorInputTransfer() {
	}

	public static EditorInputTransfer getInstance() {
		return instance;
	}

	@Override

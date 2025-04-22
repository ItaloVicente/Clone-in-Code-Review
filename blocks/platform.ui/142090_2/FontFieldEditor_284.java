			font = new Font(text.getDisplay(), fontData);
			text.setFont(font);
		}

		public int getPreferredExtent() {
			return 40;
		}
	}

	protected FontFieldEditor() {
	}

	public FontFieldEditor(String name, String labelText,
			String previewAreaText, Composite parent) {
		init(name, labelText);
		previewText = previewAreaText;
		changeButtonText = JFaceResources.getString("openChange"); //$NON-NLS-1$
		createControl(parent);

	}

	public FontFieldEditor(String name, String labelText, Composite parent) {
		this(name, labelText, null, parent);

	}

	@Override

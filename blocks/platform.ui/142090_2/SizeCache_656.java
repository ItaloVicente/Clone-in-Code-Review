				flush();
			}
		}
	}

	public Control getControl() {
		return control;
	}

	public void flush() {
		flush(true);
	}

	public void flush(boolean recursive) {
		preferredSize = null;

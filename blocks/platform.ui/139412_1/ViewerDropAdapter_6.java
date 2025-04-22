		exception.printStackTrace();
		event.detail = DND.DROP_NONE;
	}

	public abstract boolean performDrop(Object data);


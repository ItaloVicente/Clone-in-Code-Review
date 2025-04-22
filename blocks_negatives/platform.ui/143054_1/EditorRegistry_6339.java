		if (!this.filename.equals(mapping.filename)) {
			return false;
		}

		if (!this.extension.equals(mapping.extension)) {
			return false;
		}

		if (!Util.equals(this.getEditors(), mapping.getEditors())) {
			return false;
		}
		return Util.equals(this.getDeletedEditors(), mapping
				.getDeletedEditors());

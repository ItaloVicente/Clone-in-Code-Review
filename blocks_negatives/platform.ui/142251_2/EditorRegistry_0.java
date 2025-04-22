		if (!this.filename.equals(mapping.filename)) {
			return false;
		}

		if (!this.extension.equals(mapping.extension)) {
			return false;
		}

		if (!Objects.equals(this.getEditors(), mapping.getEditors())) {
			return false;
		}
		return Objects.equals(this.getDeletedEditors(), mapping.getDeletedEditors());

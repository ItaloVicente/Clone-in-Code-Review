        if (!(obj instanceof FileEditorMapping)) {
			return false;
		}
        FileEditorMapping mapping = (FileEditorMapping) obj;
        if (!this.name.equals(mapping.name)) {
			return false;
		}
        if (!this.extension.equals(mapping.extension)) {
			return false;
		}

        if (!compareList(this.editors, mapping.editors)) {
			return false;
		}
		if (!compareList(this.declaredDefaultEditors, mapping.declaredDefaultEditors)) {

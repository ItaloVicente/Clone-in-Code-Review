		FileEditorMapping mapping = (FileEditorMapping) obj;
		return Objects.equals(name, mapping.name) && Objects.equals(extension, mapping.extension)
				&& Objects.equals(editors, mapping.editors)
				&& Objects.equals(declaredDefaultEditors, mapping.declaredDefaultEditors)
				&& Objects.equals(deletedEditors, mapping.deletedEditors);
	}

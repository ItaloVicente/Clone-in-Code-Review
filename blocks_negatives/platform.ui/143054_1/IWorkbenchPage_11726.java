	 * The editor type is determined by mapping <code>editorIDs</code> to an
	 * editor extension registered with the workbench. An editor id is passed
	 * rather than an editor object to prevent the accidental creation of more
	 * than one editor for the same input. It also guarantees a consistent
	 * lifecycle for editors, regardless of whether they are created by the user
	 * or restored from saved data.

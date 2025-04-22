				}) {
			private IProject project;

			@Override
			public void format(DiffEntry ent) throws IOException,
					CorruptObjectException, MissingObjectException {
				if (DiffHeaderFormat.WORKSPACE == headerFormat) {
					IProject p = getProject(ent);
					if (!p.equals(project)) {
						project = p;
						getOutputStream().write(
								encodeASCII("#P " + project.getName() + "\n")); //$NON-NLS-1$ //$NON-NLS-2$
					}
				}
				super.format(ent);
			}
		};

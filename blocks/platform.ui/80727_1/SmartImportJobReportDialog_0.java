				return toString(project1).compareTo(toString(project2));
			}

			private String toString(IProject p) {
				IPath location = p.getLocation();
				return location == null ? "" : location.toString(); //$NON-NLS-1$

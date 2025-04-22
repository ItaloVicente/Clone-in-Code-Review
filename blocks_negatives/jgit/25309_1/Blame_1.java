				r = "^" + commit.name().substring(0, OBJECT_ID_STRING_LENGTH - 1); //$NON-NLS-1$
			else
				r = "^" + reader.abbreviate(commit, abbrev).name(); //$NON-NLS-1$
		} else {
			if (showLongRevision)
				r = commit.name();
			else
				r = reader.abbreviate(commit, abbrev + 1).name();
		}

		abbreviatedCommits.put(commit, r);
		return r;

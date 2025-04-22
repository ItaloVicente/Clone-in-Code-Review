			if (showBlankBoundary && commit.getParentCount() == 0)
				commit = null;

			if (commit == null) {
				int len = showLongRevision ? OBJECT_ID_STRING_LENGTH
						: (abbrev + 1);
				StringBuilder b = new StringBuilder(len);
				for (int i = 0; i < len; i++)
					b.append(' ');
				r = b.toString();

			} else if (!root && commit.getParentCount() == 0) {
				if (showLongRevision)
					r = "^" + commit.name().substring(0
							OBJECT_ID_STRING_LENGTH - 1);
				else
					r = "^" + reader.abbreviate(commit
			} else {
				if (showLongRevision)
					r = commit.name();
				else
					r = reader.abbreviate(commit
			}

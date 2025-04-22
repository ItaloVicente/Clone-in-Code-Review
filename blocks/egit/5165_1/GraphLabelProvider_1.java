			if (committer != null)
				switch (columnIndex) {
				case 4:
					return committer.getName()
							+ " <" + committer.getEmailAddress() + ">"; //$NON-NLS-1$ //$NON-NLS-2$
				case 5:
					return getDateFormatter().formatDate(committer);
				}

				File directory = repository.getDirectory();
				StyledString string = new StyledString();
				if (!repository.isBare())
					string.append(directory.getParentFile().getName());
				else
					string.append(directory.getName());
				string
						.append(
								" - " + directory.getAbsolutePath(), StyledString.QUALIFIER_STYLER); //$NON-NLS-1$
				String branch = repository.getBranch();
				if (repository.getRepositoryState() != RepositoryState.SAFE)
				string
						.append(
								" [" + branch + "]", StyledString.DECORATIONS_STYLER); //$NON-NLS-1$//$NON-NLS-2$
				return string;

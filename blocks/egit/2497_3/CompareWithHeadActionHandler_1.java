				next = CompareUtils.getFileRevisionTypedElement(gitPath,
						commit, repository);
			} catch (IOException e) {
				Activator.handleError(e.getMessage(), e, true);
				return null;
			}

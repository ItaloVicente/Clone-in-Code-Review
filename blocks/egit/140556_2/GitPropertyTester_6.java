			case "canCommit": { //$NON-NLS-1$
				Repository repository = AdapterUtils.adapt(receiver,
						Repository.class);
				if (repository != null) {
					return computeResult(expectedValue,
							repository.getRepositoryState().canCommit());
				}
				break;

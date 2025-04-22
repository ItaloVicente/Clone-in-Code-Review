			case "hasMultipleRefs": { //$NON-NLS-1$
				IRepositoryCommit commit = AdapterUtils.adapt(receiver,
						IRepositoryCommit.class);
				if (commit != null) {
					return computeResult(expectedValue,
							hasMultipleRefs(commit, toRefNames(args)));
				}
				break;

			case "hasRef": { //$NON-NLS-1$
				IRepositoryCommit commit = AdapterUtils.adapt(receiver,
						IRepositoryCommit.class);
				if (commit != null) {
					return computeResult(expectedValue,
							hasRef(commit, toRefNames(args)));
				}
				break;

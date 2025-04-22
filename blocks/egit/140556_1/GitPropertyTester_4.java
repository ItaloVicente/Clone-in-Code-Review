				hasRef(commit, toRefNames(args)));
		    }
			break;
		    }
			case "isStash": //$NON-NLS-1$
			{
		    RepositoryCommit commit = AdapterUtils.adapt(receiver,
			    RepositoryCommit.class);
		    return computeResult(expectedValue,
			    commit != null && commit.isStash());

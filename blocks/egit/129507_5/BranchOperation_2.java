
				IProject[] refreshProjects = results.entrySet().stream() //
					.map(this::getAffectedProjects) //
					.flatMap(arr -> Stream.of(arr)) //
					.distinct()
					.toArray(IProject[]::new);

				ProjectUtil.refreshValidProjects(refreshProjects, delete,
						progress.newChild(1));
			}

			private IProject[] getAffectedProjects(
					Entry<Repository, CheckoutResult> entry)
			{
				CheckoutResult result = entry.getValue();

				if (result.getStatus() != Status.OK
						&& result.getStatus() != Status.NONDELETED) {
					return new IProject[0];
				}

				Repository repo = entry.getKey();

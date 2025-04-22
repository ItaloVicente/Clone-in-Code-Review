				CheckoutCommand co = new Git(repository).checkout();
				co.setName(target);

				try {
					co.call();
				} catch (CheckoutConflictException e) {
					return;
				} catch (JGitInternalException e) {
					throw new CoreException(Activator.error(e.getMessage(), e));
				} catch (GitAPIException e) {
					throw new CoreException(Activator.error(e.getMessage(), e));
				} finally {
					BranchOperation.this.result = co.getResult();
				}
				if (result.getStatus() == Status.NONDELETED)
					retryDelete(result.getUndeletedList());
				progress.worked(1);


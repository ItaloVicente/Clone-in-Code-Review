						CoreText.BranchOperation_performingBranch, refName), 1);

				CheckoutCommand co = new Git(repository).checkout();
				co.setName(getTarget());

				try {
					co.call();
				} catch (JGitInternalException e) {
					throw new CoreException(Activator.error(e.getMessage(), e));
				} catch (GitAPIException e) {
					throw new CoreException(Activator.error(e.getMessage(), e));
				} finally {
					BranchOperation.this.result = co.getResult();
				}
				if (result.getStatus() == Status.NONDELETED)
					retryDelete(result.getUndeletedList());

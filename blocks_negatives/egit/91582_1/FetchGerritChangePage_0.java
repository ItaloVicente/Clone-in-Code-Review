			CheckoutCommand co = null;
			try (Git git = new Git(repository)) {
				co = git.checkout();
				co.setName(textForBranch).call();
			} catch (CheckoutConflictException e) {
				final CheckoutResult result = co.getResult();

				if (result.getStatus() == Status.CONFLICTS) {
					PlatformUI.getWorkbench().getDisplay()
							.asyncExec(new Runnable() {
						@Override
						public void run() {
							new CheckoutConflictDialog(null, repository,
									result.getConflictList()).open();

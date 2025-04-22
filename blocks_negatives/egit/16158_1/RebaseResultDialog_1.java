				try {
					final RebaseOperation op = new RebaseOperation(repo,
							Operation.ABORT);
					op.execute(null);

					show(op.getResult(), repo);
				} catch (CoreException e) {
					Activator.handleError(e.getMessage(), e, true);
				}
			else if (doNothingButton.getSelection()) {

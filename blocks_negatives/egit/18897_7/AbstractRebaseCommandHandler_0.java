							RebaseResultDialog.show(rebase.getResult(),
									repository);
							if (operation == Operation.ABORT) {
								setAmending(false, false);

							}
							if (rebase.getResult().getStatus() == Status.EDIT) {
								setAmending(true, true);

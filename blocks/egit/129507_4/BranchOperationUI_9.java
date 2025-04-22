
				if (!isSingleRepositoryOperation)
					return Status.OK_STATUS;

				CheckoutResult result = bop.getResult(repositories[0]);

				if (result.getStatus() == CheckoutResult.Status.CONFLICTS || //
						result.getStatus() == CheckoutResult.Status.NONDELETED) {
					return Status.OK_STATUS;

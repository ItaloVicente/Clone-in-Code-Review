				throw new JGitInternalException(MessageFormat.format(
						JGitText.get().checkoutUnexpectedResult
						updateResult.name()));

			if (dco != null && !dco.getToBeDeleted().isEmpty()) {
				status = new CheckoutResult(Status.NONDELETED
						dco.getToBeDeleted());
			} else

		} else if (result.getStatus() == CheckoutResult.Status.OK) {
			try {
				if (ObjectId.isId(repository.getFullBranch()))
					showDetachedHeadWarning();
			} catch (IOException e) {
			}

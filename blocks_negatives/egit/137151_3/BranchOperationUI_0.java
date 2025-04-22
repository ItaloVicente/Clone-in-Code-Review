		/*
		 * We do not have support for CreateBranchWizards when performing
		 * checkout on multiple repositories at once, thus, the
		 * showQuestionsBeforeCheckout is forced to false in this case
		 */
		this.isSingleRepositoryOperation = repositories.length == 1;
		this.showQuestionsBeforeCheckout = isSingleRepositoryOperation
				? showQuestionsBeforeCheckout
				: false;

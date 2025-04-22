				NewRepositoryWizard wiz = null;
				if (myWizard.projects.length == 1) {
					wiz = new NewRepositoryWizard(true, myWizard.projects[0]);
				} else {
					wiz = new NewRepositoryWizard(true);
				}

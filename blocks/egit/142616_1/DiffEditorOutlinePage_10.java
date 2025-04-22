					CheckoutAction action = new CheckoutAction(
							this::getStructuredSelection);
					menuManager.add(action);
					action.setEnabled(haveNew.iterator().next().getDiff()
							.getRepository().getRepositoryState()
							.equals(RepositoryState.SAFE));

			} else if (numberOfRefs == 1) {
				Repository repo = nodes.get(0).getRepository();
				IWorkbenchPage workbenchPage = HandlerUtil
						.getActiveWorkbenchWindowChecked(event).getActivePage();
				RevCommit a = repo.parseCommit(refs.get(0).getObjectId());
				compare(workbenchPage, repo, null, a.getName());

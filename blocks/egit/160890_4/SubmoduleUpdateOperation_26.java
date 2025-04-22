					@Override
					public void cloningSubmodule(String path) {
						progress.setTaskName(MessageFormat.format(
								CoreText.SubmoduleUpdateOperation_cloning,
								util.getRepositoryName(repository), path));
					}

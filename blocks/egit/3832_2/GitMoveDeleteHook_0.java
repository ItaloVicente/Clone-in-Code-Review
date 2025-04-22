				MoveResult result = moveIndexContent(dPath, srcm, sPath);
				switch (result) {
				case SUCCESS:
					break;
				case FAILED:
					tree.failed(new Status(IStatus.ERROR, Activator
							.getPluginId(), 0,
							CoreText.MoveDeleteHook_operationError, null));
					break;
				case UNTRACKED:
					return FINISH_FOR_ME;
				}

				tree.standardMoveProject(source, description, updateFlags,
						monitor);

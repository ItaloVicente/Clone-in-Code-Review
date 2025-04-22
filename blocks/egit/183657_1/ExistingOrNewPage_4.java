					IPath projectMoveTarget = null;
					if (getSeparateMode()) {
						projectMoveTarget = targetPath;
					} else {
						projectMoveTarget = targetPath.append(prj.getName());
					}

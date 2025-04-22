						if (type == IResource.FILE) {
							resource.requestResource()
									.touch(progress.newChild(1));
							touched[0] = true;
							return false;
						} else {
							IContainer container = (IContainer) resource
									.requestResource();
							IResource rsc = container
									.getFile(new Path(Constants.HEAD));
							if (rsc.exists()) {
								rsc.touch(progress.newChild(1));
								touched[0] = true;
								return false;
							}
						}

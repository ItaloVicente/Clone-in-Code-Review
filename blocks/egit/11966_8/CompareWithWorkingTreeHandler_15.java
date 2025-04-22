				IResource[] resources = new IResource[] { (IFile) input, };
				CompareUtils.compare(resources, repository, Constants.HEAD,
						dstRevCommit, true, workBenchPage);
			} else {
				IPath location = new Path(((File) input).getAbsolutePath());
				CompareUtils.compare(location, repository, Constants.HEAD,
						dstRevCommit, true, workBenchPage);
			}

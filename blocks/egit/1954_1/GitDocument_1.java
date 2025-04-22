				String msg = NLS
						.bind(UIText.GitDocument_errorLoadTree, new Object[] {
								treeId, baseline, resource, repository });
				Activator.logError(msg, new Throwable());
				setResolved(null, null, null, ""); //$NON-NLS-1$
				return;
			}
			if (!id.equals(lastBlob)) {
				if (GitTraceLocation.QUICKDIFF.isActive())
					GitTraceLocation.getTrace().trace(
							GitTraceLocation.QUICKDIFF.getLocation(),
							"(GitDocument) compareTo: " + baseline); //$NON-NLS-1$
				ObjectLoader loader = repository.open(id, Constants.OBJ_BLOB);
				byte[] bytes = loader.getBytes();
				String charset;
				charset = CompareUtils.getResourceEncoding(resource);
				String s = new String(bytes, charset);
				setResolved(commitId, treeId, id, s);
				if (GitTraceLocation.QUICKDIFF.isActive())
					GitTraceLocation
							.getTrace()
							.trace(
									GitTraceLocation.QUICKDIFF.getLocation(),
									"(GitDocument) has reference doc, size=" + s.length() + " bytes"); //$NON-NLS-1$ //$NON-NLS-2$
			} else {
				if (GitTraceLocation.QUICKDIFF.isActive())
					GitTraceLocation.getTrace().trace(
							GitTraceLocation.QUICKDIFF.getLocation(),
							"(GitDocument) already resolved"); //$NON-NLS-1$
			}
		} finally {
			if (tw != null)
				tw.release();
			if (rw != null)
				rw.release();

			} catch (IOException err) {
				String msg = NLS
						.bind(UIText.GitDocument_errorLoadCommit, new Object[] {
								commitId, baseline, resource, repository });
				Activator.logError(msg, err);
				setResolved(null, null, null, ""); //$NON-NLS-1$
				return;
			} finally {
				if (reader != null)
					reader.release();
				rw.dispose();
			}
			RevTree treeId = baselineCommit.getTree();
			if (treeId.equals(lastTree)) {
				if (GitTraceLocation.QUICKDIFF.isActive())
					GitTraceLocation.getTrace().trace(
							GitTraceLocation.QUICKDIFF.getLocation(),
				return;

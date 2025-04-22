			RevCommit commit = cmd
					.setInputStream(new ByteArrayInputStream(
							xmlContent.toString().getBytes(CHARSET)))
					.setRemoteReader(repos).setURI("platform/")
					.setTargetURI("platform/superproject")
					.setRecordRemoteBranch(true).setRecordSubmoduleLabels(true)
					.call();

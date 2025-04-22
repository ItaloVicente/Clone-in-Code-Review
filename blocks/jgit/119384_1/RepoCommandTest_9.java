			RevCommit commit = cmd
					.setInputStream(new ByteArrayInputStream(
							xmlContent.toString().getBytes(CHARSET)))
					.setRemoteReader(repos).setURI("").setTargetURI("gerrit")
					.setRecordRemoteBranch(true).setRecordSubmoduleLabels(true)
					.call();

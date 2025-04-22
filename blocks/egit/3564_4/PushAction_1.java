			if (rc.getPushRefSpecs().isEmpty())
				rc.addPushRefSpec(new RefSpec(HEAD + ":" + gsd.getDstMerge())); //$NON-NLS-1$
			PushOperationUI push = new PushOperationUI(repo, rc, timeout, false);
			push.setCredentialsProvider(new EGitCredentialsProvider());
			push.start();
		}

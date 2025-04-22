			} catch (NoHeadException e) {
				throw e;
			} catch (IOException e) {
				throw new JGitInternalException(JGitText
						.get().exceptionCaughtDuringExecutionOfPullCommand, e);
			}
			RebaseCommand rebase = new RebaseCommand(repo);
			RebaseResult rebaseRes = rebase.setUpstream(commitToMerge)

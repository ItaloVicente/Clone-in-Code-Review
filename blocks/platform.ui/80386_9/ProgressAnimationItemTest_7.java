			refresh();

			assertSingleAccessibleListener();
		} finally {
			Job.getJobManager().removeJobChangeListener(consumeEventLoopOnJobDone);
		}

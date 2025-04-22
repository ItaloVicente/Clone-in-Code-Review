			try {
				for (int done = 0; done < objectCount; done++) {
					indexOneObject();
					receiving.update(1);
					if (receiving.isCancelled())
						throw new IOException(JGitText.get().downloadCancelled);
				}
				readPackFooter();
				endInput();
			} finally {
				receiving.endTask();

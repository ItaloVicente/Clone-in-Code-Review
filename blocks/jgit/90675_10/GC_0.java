	public void collectGarbage() throws IOException
		if (gcLog != null && gcLog.lock(background)) {
			return;
		}
		Runnable gcTask = () -> {
			try {
				gc();
				if (automatic && tooManyLooseObjects() && gcLog != null) {
					String message = JGitText.get().gcTooManyUnpruned;
					gcLog.write(message.getBytes(StandardCharsets.UTF_8));
				}
			} catch (IOException | ParseException e) {
				if (background) {
					if (gcLog == null) {
						return;
					}
					try {
						gcLog.write(e.getMessage().getBytes());
						StringWriter sw = new StringWriter();
						PrintWriter pw = new PrintWriter(sw);
						e.printStackTrace(pw);
						gcLog.write(sw.toString().getBytes(StandardCharsets.UTF_8));
						gcLog.commit();
					} catch (IOException e2) {
						e2.addSuppressed(e);
						LOG.error(e2.getMessage()
					}
				} else {
					throw new JGitInternalException(e.getMessage()
				}
			} finally {
				if (gcLog != null) {
					gcLog.unlock();
				}
			}
		};
		if (background) {
			Thread thread = new Thread(gcTask);
			thread.start();
		} else {
			gcTask.run();
		}
	}


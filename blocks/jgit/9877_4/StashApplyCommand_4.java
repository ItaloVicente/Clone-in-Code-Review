		} catch (JGitInternalException e) {
			throw e;
		} catch (IOException e) {
			throw new JGitInternalException(JGitText.get().stashApplyFailed
		} finally {
			reader.release();
		}
	}

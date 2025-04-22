			loose = FileUtils.readWithRetries(path
				LooseItems result = new LooseItems();
				result.snapshot = FileSnapshot.save(f);
				result.buf = IO.readSome(f
				return result;
			});
		} catch (IOException e) {
			throw e;
		} catch (Exception e) {
			throw new IOException(
					MessageFormat.format(JGitText.get().cannotReadFile
					e);

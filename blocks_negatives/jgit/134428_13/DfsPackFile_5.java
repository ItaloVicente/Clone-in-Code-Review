			} catch (EOFException e) {
				throw new IOException(MessageFormat.format(
						DfsText.get().shortReadOfIndex,
						desc.getFileName(BITMAP_INDEX)), e);
			} catch (IOException e) {
				throw new IOException(MessageFormat.format(
						DfsText.get().cannotReadIndex,
						desc.getFileName(BITMAP_INDEX)), e);

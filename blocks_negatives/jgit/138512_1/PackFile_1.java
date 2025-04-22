				loadedIdx = idx;
			} catch (InterruptedIOException e) {
				throw e;
			} catch (IOException e) {
				invalid = true;
				throw e;

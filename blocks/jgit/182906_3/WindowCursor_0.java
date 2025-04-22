				int ptr = (int) (position - window.start);
				int n = (int) Math.min(window.size() - ptr
				window.write(out
				position += n;
				remaining -= n;
			} catch (Exception e) {
				throw new StoredPackRepresentationNotAvailableException(pack
				 e);
			}

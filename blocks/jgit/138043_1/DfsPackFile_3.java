							PackIndex idx = PackIndex
									.read(new BufferedInputStream(in
							int sz = (int) Math.min(
									idx.getObjectCount() * REC_SIZE
									Integer.MAX_VALUE);
							ctx.stats.readIdxBytes += rc.position();
							return new DfsBlockCache.Ref<>(idxKey
						} finally {
							ctx.stats.readIdxMicros += elapsedMicros(start);
						}
					} catch (EOFException e) {
						throw new IOException(MessageFormat.format(
								DfsText.get().shortReadOfIndex
								desc.getFileName(INDEX))
					} catch (IOException e) {
						throw new IOException(MessageFormat.format(
								DfsText.get().cannotReadIndex
								desc.getFileName(INDEX))
					}
				});

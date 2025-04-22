							ctx.stats.readIdxBytes += rc.position();
							return new DfsBlockCache.Ref<>(idxKey, 0, sz, idx);
						} finally {
							ctx.stats.readIdxMicros += elapsedMicros(start);
						}
					} catch (EOFException e) {
						throw new IOException(MessageFormat.format(
								DfsText.get().shortReadOfIndex,
								desc.getFileName(INDEX)), e);
					} catch (IOException e) {
						throw new IOException(MessageFormat.format(
								DfsText.get().cannotReadIndex,
								desc.getFileName(INDEX)), e);
					}
				});

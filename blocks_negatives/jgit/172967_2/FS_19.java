				FileTime t1 = Files.getLastModifiedTime(probe);
				FileTime t2 = t1;
				Instant t1i = t1.toInstant();
				for (long i = 1; t2.compareTo(t1) <= 0; i += 1 + i / 20) {
					Files.setLastModifiedTime(probe,
							FileTime.from(t1i.plusNanos(i * 1000)));
					t2 = Files.getLastModifiedTime(probe);
				}
				Duration fsResolution = Duration.between(t1.toInstant(), t2.toInstant());

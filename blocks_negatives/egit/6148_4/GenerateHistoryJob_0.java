
					final long now = System.currentTimeMillis();
					if (now - lastUpdateAt < 2000 && lastUpdateCnt > 0)
						continue;
					updateUI(incomplete);
					lastUpdateAt = now;

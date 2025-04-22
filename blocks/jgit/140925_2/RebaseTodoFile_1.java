				String commitToken;
				if (nextSpace > lineEnd + 1) {
					commitToken = new String(buf
							lineEnd - tokenBegin + 1
				} else {
					commitToken = new String(buf
							nextSpace - tokenBegin - 1
				}

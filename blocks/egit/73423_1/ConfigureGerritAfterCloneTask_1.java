						tmpPath = path.substring(0, slash);
						slash++;
						break;
					default:
						return false;
					}
				} catch (IOException e) {
					return false;
				} finally {
					if (httpConnection != null) {
						httpConnection.disconnect();

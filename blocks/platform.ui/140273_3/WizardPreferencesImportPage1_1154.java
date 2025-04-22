					}
				}

				PreferenceTransferElement[] destTransfers = new PreferenceTransferElement[index];
				System.arraycopy(returnTransfers, 0, destTransfers, 0, index);
				return destTransfers;
			} catch (CoreException e) {
			} finally {
				try {
					fis.close();
				} catch (IOException e) {
					WorkbenchPlugin.log(e.getMessage(), e);
				}
			}
		}

		return new PreferenceTransferElement[0];
	}

	private boolean validFromFile() {
		File fromFile = new File(getDestinationValue());
		return fromFile.exists() && !fromFile.isDirectory();
	}

	@Override

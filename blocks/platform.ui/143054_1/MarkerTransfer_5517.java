			int n = in.readInt();

			IMarker[] markers = new IMarker[n];
			for (int i = 0; i < n; i++) {
				IMarker marker = readMarker(in);
				if (marker == null) {
					return null;
				}
				markers[i] = marker;
			}
			return markers;
		} catch (IOException e) {
			return null;
		}
	}

	private IMarker readMarker(DataInputStream dataIn) throws IOException {
		String path = dataIn.readUTF();
		long id = dataIn.readLong();
		return findMarker(path, id);
	}

	private void writeMarker(IMarker marker, DataOutputStream dataOut)
			throws IOException {

		dataOut.writeUTF(marker.getResource().getFullPath().toString());
		dataOut.writeLong(marker.getId());
	}

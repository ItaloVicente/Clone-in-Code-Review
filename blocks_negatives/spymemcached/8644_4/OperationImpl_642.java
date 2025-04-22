	@Override
	public void readFromBuffer(ByteBuffer data) throws IOException {
		while(getState() != OperationState.COMPLETE && data.remaining() > 0) {
			if(readType == OperationReadType.DATA) {
				handleRead(data);
			} else {
				int offset=-1;
				for(int i=0; data.remaining() > 0; i++) {
					byte b=data.get();
					if(b == '\r') {
						foundCr=true;
					} else if(b == '\n') {
						assert foundCr: "got a \\n without a \\r";
						offset=i;
						foundCr=false;
						break;
					} else {
						assert !foundCr : "got a \\r without a \\n";
						byteBuffer.write(b);
					}
				}
				if(offset >= 0) {
					String line=new String(byteBuffer.toByteArray(), CHARSET);
					byteBuffer.reset();
					OperationErrorType eType=classifyError(line);
					if(eType != null) {
						handleError(eType, line);
					} else {
						handleLine(line);
					}
				}
			}
		}
	}

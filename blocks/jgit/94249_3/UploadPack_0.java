				output = o;
			}

			rawOut = new ResponseBufferedOutputStream(output);
			if (biDirectionalPipe) {
				rawOut.stopBuffering();

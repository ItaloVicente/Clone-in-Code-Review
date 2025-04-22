			return ldr.getBytes(binaryFileThreshold);

		} catch (LargeObjectException.ExceedsLimit overLimit) {
			return BINARY;

		} catch (LargeObjectException.ExceedsByteArrayLimit overLimit) {
			return BINARY;

		} catch (LargeObjectException.OutOfMemory tooBig) {
			return BINARY;


	/** @return obtain the current CRC32 register. */
	int getCRC32() {
		return (int) crc.getValue();
	}

	/** Reinitialize the CRC32 register for a new region. */
	void resetCRC32() {
		crc.reset();
	}


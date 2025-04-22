    	if (idxToRemove == 0) {
    		System.arraycopy(src, 1, dst, 0, src.length - 1);
    	}
    	else if (idxToRemove == src.length - 1) {
    		System.arraycopy(src, 0, dst, 0, src.length - 1);
    	}
    	else {
    		System.arraycopy(src, 0, dst, 0, idxToRemove);
    		System.arraycopy(src, idxToRemove + 1, dst, idxToRemove, src.length - idxToRemove - 1);
    	}
    }

    /**
     * Appends array2 to the end of array1 and returns the result
     *
     * @param array1
     * @param array2
     * @return
     * @since 3.1
     */
    public static Object[] appendArray(Object[] array1, Object[] array2) {
        Object[] result = new Object[array1.length + array2.length];
        System.arraycopy(array1, 0, result, 0, array1.length);
        System.arraycopy(array2, 0, result, array1.length, array2.length);
        return result;
    }

    private Util() {
    }

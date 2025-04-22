        return result;
    }

    private String computePreferredSizeString() {
        StringBuilder result = new StringBuilder();
        result.append("/* (non-Javadoc)\n");
        result.append(" * @see org.eclipse.ui.ISizeProvider#computePreferredSize(boolean, int, int, int)\n");
        result.append(" */\n");
        result.append("public int computePreferredSize(boolean width, int availableParallel,\n");
        result.append("\tint availablePerpendicular, int preferredResult) {\n");
        result.append("\tint result = preferredResult;\n");
        if (fixedArea != ISizeProvider.INFINITE) {
            result.append("\tresult = (availablePerpendicular != 0) ? " + fixedArea + "/availablePerpendicular : 0;\n");
            result.append("\tif (result < 30) result = 30;\n");
        }
        result.append("\tif (width) {\n");
        if (quantizedWidth != ISizeProvider.INFINITE && quantizedWidth != 0) {
            result.append("\t\tresult = Math.min(result + " + quantizedWidth + "/2, availableParallel);\n");
            result.append("\t\tresult = result - (result % " + quantizedWidth + ");\n");
        }
        if (minWidth != ISizeProvider.INFINITE) {
            result.append("\t\tif (result < " + minWidth + ") result = " + minWidth + ";\n");
        }
        if (maxWidth != ISizeProvider.INFINITE) {
            result.append("\t\tif (result > " + maxWidth + ") result = " + maxWidth + ";\n");
        }
        result.append("\t} else {\n");
        if (quantizedHeight != ISizeProvider.INFINITE && quantizedHeight != 0) {
            result.append("\t\tresult = Math.min(result + " + quantizedHeight + "/2, availableParallel);\n");
            result.append("\t\tresult = result - (result % " + quantizedHeight + ");\n");
        }
        if (minHeight != ISizeProvider.INFINITE) {
            result.append("\t\tif (result < " + minHeight + ") result = " + minHeight + ";\n");
        }
        if (maxHeight != ISizeProvider.INFINITE) {
            result.append("\t\tif (result > " + maxHeight + ") result = " + maxHeight + ";\n");
        }
        result.append("\t}\n");
        result.append("\tif (result > availableParallel) result = availableParallel;\n");
        result.append("\tif (result < 0) result = 0;\n");
        result.append("\treturn result;\n");
        result.append("}\n");
        return result.toString();
    }

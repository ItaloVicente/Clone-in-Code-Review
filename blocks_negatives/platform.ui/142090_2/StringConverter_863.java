        Assert.isNotNull(value);
        StringBuilder buffer = new StringBuilder();
        buffer.append(value.getName());
        buffer.append(SEPARATOR);
        int style = value.getStyle();
        boolean bold = (style & SWT.BOLD) == SWT.BOLD;
        boolean italic = (style & SWT.ITALIC) == SWT.ITALIC;
        if (bold && italic) {
        } else if (bold) {
        } else if (italic) {
        } else {
        }
        buffer.append(SEPARATOR);
        buffer.append(value.getHeight());
        return buffer.toString();

    }

    public static String asString(Rectangle value) {
        Assert.isNotNull(value);
        StringBuilder buffer = new StringBuilder();
        buffer.append(value.x);
        buffer.append(',');
        buffer.append(value.y);
        buffer.append(',');
        buffer.append(value.width);
        buffer.append(',');
        buffer.append(value.height);
        return buffer.toString();
    }

    /**

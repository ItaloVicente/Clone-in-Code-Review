        if (string == null) {
            final StringBuilder stringBuffer = new StringBuilder();
            stringBuffer.append('[');
            stringBuffer.append(categoryActivityBindings);
            stringBuffer.append(',');
            stringBuffer.append(defined);
            stringBuffer.append(',');
            stringBuffer.append(id);
            stringBuffer.append(',');
            stringBuffer.append(name);
            stringBuffer.append(']');
            string = stringBuffer.toString();
        }

        return string;
    }

    @Override

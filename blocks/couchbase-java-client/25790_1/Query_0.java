        ParsePosition pp = new ParsePosition(0);
        NumberFormat numberFormat = NumberFormat.getInstance();
        Number result = numberFormat.parse(value.toString(), pp);
        if (pp.getIndex() == value.toString().length()) {
            encoded = result.toString();
        } else {
            encoded = "\"" + value.toString() + "\"";
        }

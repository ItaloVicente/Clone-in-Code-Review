        if (this.value != null) {
            boolean found = false;
            for (Button radio : radioButtons) {
                boolean selection = false;
                if (((String) radio.getData()).equals(this.value)) {
                    selection = true;
                    found = true;
                }
                radio.setSelection(selection);
            }
            if (found) {

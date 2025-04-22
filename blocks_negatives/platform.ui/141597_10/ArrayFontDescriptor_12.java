            for (int i = 0; i < data.length; i++) {
                FontData fd = data[i];
                FontData fd2 = descr.data[i];

                if (!fd.equals(fd2)) {
                    return false;
                }
            }


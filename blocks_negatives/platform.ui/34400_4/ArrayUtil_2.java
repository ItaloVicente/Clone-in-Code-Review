        if (one.length != two.length)
            return false;
        else {
            for (int i = 0; i < one.length; i++)
                if (one[i] != two[i])
                    return false;
            return true;
        }

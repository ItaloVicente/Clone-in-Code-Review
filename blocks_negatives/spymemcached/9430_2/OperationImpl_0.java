    return (data[i] & 0xff) << 56
      | (data[i + 1] & 0xff) << 48
      | (data[i + 2] & 0xff) << 40
      | (data[i + 3] & 0xff) << 32
      | (data[i + 4] & 0xff) << 24
      | (data[i + 5] & 0xff) << 16
      | (data[i + 6] & 0xff) << 8
      | (data[i + 7] & 0xff);

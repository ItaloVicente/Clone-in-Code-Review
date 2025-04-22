    return (data[i] & 0xffL) << 56
      | (data[i + 1] & 0xffL) << 48
      | (data[i + 2] & 0xffL) << 40
      | (data[i + 3] & 0xffL) << 32
      | (data[i + 4] & 0xffL) << 24
      | (data[i + 5] & 0xffL) << 16
      | (data[i + 6] & 0xffL) << 8
      | (data[i + 7] & 0xffL);

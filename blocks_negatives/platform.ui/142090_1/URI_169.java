    int i = value.indexOf('%');
    if (i < 0)
    {
      return value;
    }
    else
    {
      StringBuilder result = new StringBuilder(value.substring(0, i));
      byte [] bytes = new byte[4];
      int receivedBytes = 0;
      int expectedBytes = 0;
      for (int len = value.length(); i < len; i++)
      {
        if (isEscaped(value, i))
        {
          char character = unescape(value.charAt(i + 1), value.charAt(i + 2));
          i += 2;

          if (expectedBytes > 0)
          {
            if ((character & 0xC0) == 0x80)
            {
              bytes[receivedBytes++] = (byte)character;
            }
            else
            {
              expectedBytes = 0;
            }
          }
          else if (character >= 0x80)
          {
            if ((character & 0xE0) == 0xC0)
            {
              bytes[receivedBytes++] = (byte)character;
              expectedBytes = 2;
            }
            else if ((character & 0xF0) == 0xE0)
            {
              bytes[receivedBytes++] = (byte)character;
              expectedBytes = 3;
            }
            else if ((character & 0xF8) == 0xF0)
            {
              bytes[receivedBytes++] = (byte)character;
              expectedBytes = 4;
            }
          }

          if (expectedBytes > 0)
          {
            if (receivedBytes == expectedBytes)
            {
              switch (receivedBytes)
              {
                case 2:
                {
                  result.append((char)((bytes[0] & 0x1F) << 6 | bytes[1] & 0x3F));
                  break;
                }
                case 3:
                {
                  result.append((char)((bytes[0] & 0xF) << 12 | (bytes[1] & 0X3F) << 6 | bytes[2] & 0x3F));
                  break;
                }
                case 4:
                {
                  result.appendCodePoint(((bytes[0] & 0x7) << 18 | (bytes[1] & 0X3F) << 12 | (bytes[2] & 0X3F) << 6 | bytes[3] & 0x3F));
                  break;
                }
              }
              receivedBytes = 0;
              expectedBytes = 0;
            }
          }
          else
          {
            for (int j = 0; j < receivedBytes; ++j)
            {
              result.append((char)bytes[j]);
            }
            receivedBytes = 0;
            result.append(character);
          }
        }
        else
        {
          for (int j = 0; j < receivedBytes; ++j)
          {
            result.append((char)bytes[j]);
          }
          receivedBytes = 0;
          result.append(value.charAt(i));
        }
      }
      return result.toString();
    }
  }

  private static char unescape(char highHexDigit, char lowHexDigit)
  {
    return (char)((valueOf(highHexDigit) << 4) | valueOf(lowHexDigit));
  }

  private static int valueOf(char hexDigit)
  {
    if (hexDigit >= 'A' && hexDigit <= 'F')
    {
      return hexDigit - 'A' + 10;
    }
    if (hexDigit >= 'a' && hexDigit <= 'f')
    {
      return hexDigit - 'a' + 10;
    }
    if (hexDigit >= '0' && hexDigit <= '9')
    {
      return hexDigit - '0';
    }
    return 0;
  }

  /*
   * Returns <code>true</code> if this URI contains non-ASCII characters;
   * <code>false</code> otherwise.
   *
   * This unused code is included for possible future use...
   */

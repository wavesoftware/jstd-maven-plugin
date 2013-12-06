package pl.wavesoftware.jstdmavenplugin;

/**
 * Copyright 2009-2011, Burke Webster (burke.webster@gmail.com)
 */
public class UrlBuilder
{
    public static String build(String... parts)
    {
		StringBuilder buffer = new StringBuilder();
        for (String part : parts)
        {
            buffer.append(part);
        }
        return buffer.toString().replaceAll("[/]{2,}", "/");
    }
}

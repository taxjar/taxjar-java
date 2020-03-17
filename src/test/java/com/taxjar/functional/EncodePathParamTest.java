package com.taxjar.functional;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URISyntaxException;

import com.taxjar.Taxjar;

import junit.framework.TestCase;

public class EncodePathParamTest extends TestCase {
  public void testEncodePathParam() throws URISyntaxException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
    Method encodePathParam = Taxjar.class.getDeclaredMethod("encodePathParam", String.class);
    encodePathParam.setAccessible(true);
    String pathParam = (String) encodePathParam.invoke(null, "123 456");

    assertEquals("123%20456", pathParam);
  }
}

package org.okeydokey.backend.utils;

/**
 * <pre>
 * Utility aim at logging byte array
 * </pre>
 * 
 * @author <a href="mailto:johunsang@gmail.com">hunsang jo</a>
 * @version 1.0
 * @since 2015.05.23
 */
public class HexDumpUtil {

	/**
	 * The upper 4 bit part of 8 bit. 
	 */
    private static final byte[] highDigits;
    /**
     * The lower 4 bit part of 8 bit. 
     */
    private static final byte[] lowDigits;
    
    /**
     * String prefix of hex value
     */
    private static String HEX_PREFIX = "0x";
    
    static {
        final byte[] digits = { '0', '1', '2', '3', '4', '5', '6', '7', '8',
                '9', 'A', 'B', 'C', 'D', 'E', 'F' };
        
        int i;
        byte[] high = new byte[256];
        byte[] low = new byte[256];

        for (i = 0; i < 256; i++) {
            high[i] = digits[i >>> 4];
            low[i] = digits[i & 0x0F];
        }

        highDigits = high;
        lowDigits = low;
    }
    
    /**
     * returns hex string expression of given byte.<p>
     * 
     * <pre><blockquote><code>
     * return "14" if b = (byte) 20; 
     * return "1D" if b = (byte) 29; 
     * return "FF" if b = (byte) 255;
     * </code></blockquote></pre>
     * 
     * @param 	b byte
     * @return	Hex string
     */
    public static String toHex(byte b) {
    	
    	int byteValue = b & 0xFF;
    	
    	char h = (char) highDigits[byteValue];
    	char l = (char) lowDigits[byteValue];
    	
    	return "" + h + l;
    	
    }
    
    /**
     * returns hex string expression of given byte with hex prefix.<p>
     * <pre><blockquote><code>
     * return "0x14" if b = (byte) 20; 
     * return "0x1D" if b = (byte) 29; 
     * return "0xFF" if b = (byte) 255;
     * </code></blockquote></pre>
     * 
     * @param b byte
     * @return hex string expression
     */
    public static String toHexWithPrefix(byte b) {
    	return HEX_PREFIX + toHex(b);
    }
    
    /**
     * Returns hex string expression of given byte array.<p>
     * use toHex(byte) method internally.
     * 
     * @param 	b byte array
     * @return	Hex string; null if input byte array is null and empty string if byte length is 0
     */
    public static String toHexDump(byte[] b) {
    	
    	if (b == null)
    		return "";
    	
    	return toHexDump(b, 0, b.length);
    }
    
    /**
     * returns hex string expression of given subarray of bytes.<p> 
     * 
     * @param b
     * @param offset the index of the first byte to decode
     * @param length the number of bytes to express
     * @return Hex string; null if input byte array is null and empty string if byte length is 0
     */
    public static String toHexDump(byte[] b, int offset, int length) {
    	
    	if (b == null)
    		return null;
    	
    	if (b.length == 0) {
    		return "";
    	} else {
    		StringBuffer sbuf = new StringBuffer();
    		sbuf.append(toHex(b[offset]));
    		
    		for (int i = 1; i < length; i++) {
    			sbuf.append(" " + toHex(b[offset + i]));
    		}
    		return sbuf.toString();
    	}
    	
    }
    
    
    /**
     * returns byte of given hex string expression.
     * <pre><blockquote><code>
     * String s1 = "0xff";
     * String s2 = "ff";
     * 
     * byte b1 = HexDumpUtil.toByte(s1); 	// b1 = -1
     * byte b2 = HexDumpUtil.toByte(s2);	// b2 = -1
     * </code></blockquote></pre>
     * 
     * @param 	hex hex string
     * @return	byte
     */
    public static byte toByte(String hex) {
    	
    	if (hex.startsWith(HEX_PREFIX)) {
    		hex = hex.replace(HEX_PREFIX, "");
    	}
    	
    	int i = Integer.parseInt(hex, 16);
    	
    	return (byte) i;
    }
    
    
}

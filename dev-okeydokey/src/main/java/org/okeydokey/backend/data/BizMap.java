package org.okeydokey.backend.data;

import java.math.BigDecimal;
import java.util.HashMap;

/**
 * <pre>
 * BizMap is wrapper class of HashMap
 * </pre>
 * 
 * @author <a href="mailto:johunsang@gmail.com">hunsang jo</a>
 * @version 1.0
 * @since 2015.01.01
 */
@SuppressWarnings("serial")
public class BizMap<K, V> extends HashMap<String, Object> {
	/**
	 * get Object value
	 * 
	 * @param key
	 * @return Object value
	 */
	public Object get(java.lang.String key) {

		if (key == null) {
			return null;
		}

		Object value = null;

		value = super.get(key);

		return value;
	}

	/**
	 * get String value
	 * 
	 * @param key
	 * @return String value
	 */
	public String getString(java.lang.String key) {
		if (key == null) {
			return "";
		}

		Object value = get(key);

		if (value == null) {
			return "";
		}

		return value.toString();
	}

	/**
	 * get int value
	 * 
	 * @param key
	 * @return int value
	 */
	public int getInt(java.lang.String key) {
		if (key == null) {
			return 0;
		}

		Object value = get(key);

		if (value == null) {
			return 0;
		}

		if (value instanceof java.lang.String) {
			try {
				return Integer.parseInt((String) value);
			} catch (NumberFormatException e) {
				return 0;
			}
		} else if (value instanceof java.lang.Number) {
			return ((Number) value).intValue();
		} else {
			return 0;
		}
	}


	/**
	 * get long value
	 * 
	 * @param key
	 * @return long value
	 */
	public long getLong(java.lang.String key) {
		if (key == null) {
			return 0;
		}

		Object value = get(key);

		if (value == null) {
			return 0;
		}

		if (value instanceof java.lang.String) {
			try {
				return Long.parseLong((String) value);
			} catch (NumberFormatException e) {
				return 0;
			}
		} else if (value instanceof java.lang.Number) {
			return ((Number) value).longValue();
		} else {
			return 0;
		}
	}

	/**
	 * get double value
	 * 
	 * @param key
	 * @return double value
	 */
	public double getDouble(java.lang.String key) {
		if (key == null) {
			return 0;
		}

		Object value = get(key);

		if (value == null) {
			return 0;
		}

		if (value instanceof java.lang.String) {
			try {
				return Double.parseDouble((String) value);
			} catch (NumberFormatException e) {
				return 0;
			}
		} else if (value instanceof java.lang.Number) {
			return ((Number) value).doubleValue();
		} else {
			return 0;
		}
	}

	/**
	 * get float value
	 * 
	 * @param key
	 * @return float value
	 */
	public float getFloat(java.lang.String key) {
		if (key == null) {
			return 0;
		}

		Object value = get(key);

		if (value == null) {
			return 0;
		}

		if (value instanceof java.lang.String) {
			try {
				return Float.parseFloat((String) value);
			} catch (NumberFormatException e) {
				return 0;
			}
		} else if (value instanceof java.lang.Number) {
			return ((Number) value).floatValue();
		} else {
			return 0;
		}
	}

	/**
	 * get BigDecimal value
	 * 
	 * @param key
	 * @return BigDecimal
	 */
	public BigDecimal getBigDecimal(java.lang.String key) {

		if (key == null) {
			return new BigDecimal(0);
		}

		Object value = get(key);

		if (value == null) {
			return new BigDecimal(0);
		}

		if (value instanceof BigDecimal) {
			return (BigDecimal) value;
		} else if (value instanceof Short || value instanceof Integer || value instanceof Long) {
			return BigDecimal.valueOf(((Number) value).longValue());
		} else if (value instanceof Float) {
			return new BigDecimal(String.valueOf(value));
		} else if (value instanceof Number) {
			return BigDecimal.valueOf(((Number) value).doubleValue());
		} else if (value instanceof String) {
			String valueStr = (String) value;
			try {
				return new BigDecimal(valueStr.trim());
			} catch (NumberFormatException e) {
				return new BigDecimal(0);
			}
		} else {
			return new BigDecimal(0);
		}

	}

}

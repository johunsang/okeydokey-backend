/* ------------------------------------------------------------------------
 *    Copyright (C) 2015  www.okeydokeyframework.org
 *
 *    This program is free software; you can redistribute it and/or modify
 *    it under the terms of the GNU General Public License as published by
 *    the Free Software Foundation; either version 2 of the License, or
 *    (at your option) any later version.
 *    
 *    This program is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU General Public License for more details.
 *
 *    You should have received a copy of the GNU General Public License along
 *    with this program; if not, write to the Free Software Foundation, Inc.,
 *    51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 * ------------------------------------------------------------------------ 
 */
package org.okeydokey.backend.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * <pre>
 * Property Loader
 *  
 * 1.code.properties
 * 2.message.properties
 * 3.config.properties
 * 4.code.properties
 * </pre>
 * 
 * @author <a href="mailto:johunsang@gmail.com">hunsang jo</a>
 * @version 1.0
 * @since 2015.05.23
 */
public final class PropertyLoader {

	/**
	 * config property
	 */
	private Properties configProp;

	/**
	 * code property
	 */
	private Properties codeProp;

	/**
	 * message property
	 */
	private Properties messageProp;

	/**
	 * okeydokey property
	 */
	private Properties okeydokeyProp;

	/**
	 * config property filename
	 */
	private String configPropertyFile;

	/**
	 * code property filename
	 */
	private String codePropertyFile;

	/**
	 * message property filename
	 */
	private String messagePropertyFile;

	/**
	 * okeydokey property filename
	 */
	private String okeydokeyPropertyFile;

	/**
	 * single instance
	 */
	private volatile static PropertyLoader instance;

	/**
	 * Return single instance of PropertyLoader
	 * 
	 * @return single instance of PropertyLoader
	 */
	public synchronized static PropertyLoader getPropertyInstance() {
		if (instance == null) {
			synchronized (PropertyLoader.class) {
				if (instance == null) {
					instance = new PropertyLoader();
				}
			}
		}
		return instance;
	}

	/**
	 * prevent instantiation
	 */
	private PropertyLoader() {

	}

	/**
	 * set Property files
	 * 
	 * @param configPropertyFile
	 * @param codePropertyFile
	 * @param messagePropertyFile
	 * @param okeydokeyPropertyFile
	 * @return boolean
	 */
	public boolean setPropertyFile(String configPropertyFile, String codePropertyFile, String messagePropertyFile, String okeydokeyPropertyFile) {

		configProp = new Properties();
		codeProp = new Properties();
		messageProp = new Properties();
		okeydokeyProp = new Properties();

		this.configPropertyFile = configPropertyFile;
		this.codePropertyFile = codePropertyFile;
		this.messagePropertyFile = messagePropertyFile;
		this.okeydokeyPropertyFile = okeydokeyPropertyFile;

		File configFile = new File(configPropertyFile);
		File codeFile = new File(codePropertyFile);
		File messageFile = new File(messagePropertyFile);
		File okeydokeyFile = new File(okeydokeyPropertyFile);

		if (!configFile.exists() || !configFile.isFile()) {
			return false;
		}
		if (!codeFile.exists() || !codeFile.isFile()) {
			return false;
		}
		if (!messageFile.exists() || !messageFile.isFile()) {
			return false;
		}
		if (!okeydokeyFile.exists() || !okeydokeyFile.isFile()) {
			return false;
		}

		InputStream configIn = null;
		InputStream codeIn = null;
		InputStream messageIn = null;
		InputStream okeydokeyIn = null;

		try {
			configIn = new FileInputStream(this.configPropertyFile);
			codeIn = new FileInputStream(this.codePropertyFile);
			messageIn = new FileInputStream(this.messagePropertyFile);
			okeydokeyIn = new FileInputStream(this.okeydokeyPropertyFile);

			if (null != configIn) {
				configProp.load(configIn);
			}
			if (null != codeIn) {
				codeProp.load(codeIn);
			}
			if (null != messageIn) {
				messageProp.load(messageIn);
			}
			if (null != okeydokeyIn) {
				okeydokeyProp.load(okeydokeyIn);
			}

		} catch (IOException e) {
		} finally {
			try {
				configIn.close();
			} catch (IOException e) {
			}
			try {
				codeIn.close();
			} catch (IOException e) {
			}
			try {
				messageIn.close();
			} catch (IOException e) {
			}
			try {
				okeydokeyIn.close();
			} catch (IOException e) {
			}
		}
		return true;
	}

	public String getConfigPropertyFile() {
		return configPropertyFile;
	}

	public String getCodePropertyFile() {
		return codePropertyFile;
	}

	public String getMessagePropertyFile() {
		return messagePropertyFile;
	}

	public String getOkeydokeyPropertyFile() {
		return okeydokeyPropertyFile;
	}

	/**
	 * Get Config Value
	 * 
	 * @param key
	 * @return value
	 */
	public String getConfig(String key) {

		if (key == null || key.equals(""))
			return "";
		return (configProp.getProperty(key) == null) ? "" : configProp.getProperty(key).trim();
	}

	/**
	 * Get Code Value
	 * 
	 * @param key
	 * @return value
	 */
	public String getCode(String code) {

		if (code == null || code.equals(""))
			return "";
		return (codeProp.getProperty(code) == null) ? "" : codeProp.getProperty(code).trim();
	}

	/**
	 * Get Message Value
	 * 
	 * @param key
	 * @return value
	 */
	public String getMessage(String id) {

		if (id == null || id.equals(""))
			return "";
		return (messageProp.getProperty(id) == null) ? "" : messageProp.getProperty(id).trim();
	}

	/**
	 * Get Okeydokey Value
	 * 
	 * @param key
	 * @return value
	 */
	public String getOkeydokey(String id) {

		if (id == null || id.equals(""))
			return "";
		return (okeydokeyProp.getProperty(id) == null) ? "" : okeydokeyProp.getProperty(id).trim();
	}

	/**
	 * Get Config int Value
	 * 
	 * @param key
	 * @return value
	 */
	public int getIntConfig(String key) {
		String value = getConfig(key);
		int iValue = 0;
		if (value != null && !value.equals("")) {
			iValue = Integer.parseInt(value);
		}
		return iValue;
	}

	/**
	 * Get OkeyDokey int Value
	 * 
	 * @param key
	 * @return value
	 */
	public int getIntOkeydokey(String key) {
		String value = getOkeydokey(key);
		int iValue = 0;
		if (value != null && !value.equals("")) {
			iValue = Integer.parseInt(value);
		}
		return iValue;
	}
}

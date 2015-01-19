package org.okeydokey.backend.app;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.ClassFile;
import javassist.bytecode.annotation.Annotation;

import org.okeydokey.backend.biz.AfterBiz;
import org.okeydokey.backend.biz.BeforeBiz;
import org.okeydokey.backend.biz.Biz;
import org.okeydokey.backend.consts.AppConstants;
import org.okeydokey.backend.utils.StringUtil;

/**
 * <pre>
 * Implementing java.nio.file.FileVisitor that visit class file(/WEB-INF/classes) and scan it to find @Biz annotation
 * </pre>
 * 
 * @author <a href="mailto:johunsang@gmail.com">hunsang jo</a>
 * @version 1.0
 * @since 2015.05.23
 */
public class AnnotationBizClassFileVisitor implements FileVisitor<Path> {

	/**
	 * Store map of biz class
	 */
	private Map<String, String> bizClassIndex = new HashMap<String, String>();

	/**
	 * Store map of before biz class
	 */
	private Map<Integer, String> beforeClassIndex = new TreeMap<Integer, String>();

	/**
	 * Store map of after biz class
	 */
	private Map<Integer, String> afterClassIndex = new TreeMap<Integer, String>();

	@Override
	public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
		return FileVisitResult.CONTINUE;
	}

	@Override
	public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
		return FileVisitResult.CONTINUE;
	}

	@Override
	public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
		return FileVisitResult.CONTINUE;
	}

	@Override
	public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
		File classfile = file.toFile();
		// Read .class files
		if (classfile.canRead() && classfile.length() > 0 && classfile.getName().endsWith(AppConstants.CLASS_EXTENSION)) {
			deserializeClass(new FileInputStream(classfile));
		}
		return FileVisitResult.CONTINUE;
	}

	/**
	 * Deserialize file to Class
	 * 
	 * @param input
	 *            FileInputStream of class file
	 * @throws IOException
	 */
	public void deserializeClass(InputStream input) throws IOException {
		DataInputStream dataInput = new DataInputStream(new BufferedInputStream(input));
		ClassFile cfile = null;
		try {
			cfile = new ClassFile(dataInput);
			scanClass(cfile);
		} finally {
			dataInput.close();
			input.close();
		}
	}

	/**
	 * Scan class and get annotation class
	 * 
	 * @param cfile
	 *            ClassFile
	 */
	protected void scanClass(ClassFile cfile) {
		String className = cfile.getName();
		// visible annotation
		AnnotationsAttribute visible = (AnnotationsAttribute) cfile.getAttribute(AnnotationsAttribute.visibleTag);
		if (visible != null) {
			Annotation[] annos = visible.getAnnotations();
			for (Annotation annotation : annos) {
				String annoName = annotation.getTypeName();
				// @Biz
				if (StringUtil.equals(annoName, Biz.class.getName())) {
					setBizClassMap(annotation, className);
					// @BeforeBiz
				} else if (StringUtil.equals(annoName, BeforeBiz.class.getName())) {
					setBeforeAfterBizClassMap(annotation, className, "before");
					// @AfterBiz
				} else if (StringUtil.equals(annoName, AfterBiz.class.getName())) {
					setBeforeAfterBizClassMap(annotation, className, "after");
				}
			}
			return;
		}
	}

	private void setBizClassMap(Annotation annotation, String className) {
		String bizId = "";
		// find bizId
		Iterator<?> itrr = annotation.getMemberNames().iterator();
		while (itrr.hasNext()) {
			String annoMememberName = (String) itrr.next();
			if (StringUtil.equals(AppConstants.BIZID, annoMememberName)) {
				bizId = annotation.getMemberValue(annoMememberName).toString();
			}
			if (!StringUtil.isEmpty(bizId)) {
				bizId = bizId.replaceAll("\"", "");
			} else {
				return;
			}
			break;
		}
		// Set bizClassIndex
		bizClassIndex.put(bizId, className);
	}

	private void setBeforeAfterBizClassMap(Annotation annotation, String className, String type) {
		String seq = "";
		// find seq
		Iterator<?> itrr = annotation.getMemberNames().iterator();
		while (itrr.hasNext()) {
			String annoMememberName = (String) itrr.next();
			if (StringUtil.equals(AppConstants.SEQ, annoMememberName)) {
				seq = annotation.getMemberValue(annoMememberName).toString();
			}
			if (!StringUtil.isEmpty(seq)) {
				seq = seq.replaceAll("\"", "");
			} else {
				return;
			}
		}
		if (StringUtil.equals(type, "before")) {
			beforeClassIndex.put(Integer.parseInt(seq), className);
		} else {
			afterClassIndex.put(Integer.parseInt(seq), className);
		}
	}

	public Map<String, String> getBizClassIndex() {
		return bizClassIndex;
	}

	public void setBizClassIndex(Map<String, String> bizClassIndex) {
		this.bizClassIndex = bizClassIndex;
	}

	public Map<Integer, String> getBeforeBizClassIndex() {
		return beforeClassIndex;
	}

	public void setBeforeBizClassIndex(Map<Integer, String> beforeClassIndex) {
		this.beforeClassIndex = beforeClassIndex;
	}

	public Map<Integer, String> getAfterBizClassIndex() {
		return afterClassIndex;
	}

	public void setAfterBizClassIndex(Map<Integer, String> afterClassIndex) {
		this.afterClassIndex = afterClassIndex;
	}
}